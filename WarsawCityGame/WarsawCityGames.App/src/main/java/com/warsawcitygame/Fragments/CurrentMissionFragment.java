package com.warsawcitygame.Fragments;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygame.Activities.LoginActivity;
import com.warsawcitygame.Activities.MapsActivity;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DelegateAction;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.LocationService;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.CurrentMissionModel;
import com.warsawcitygames.models.MissionModel;
import com.warsawcitygames.models.PlayerProfileDataModel;
import com.warsawcitygames.models.UserMissionModel;
import com.warsawcitygamescommunication.Services.MissionsService;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

import static com.warsawcitygame.Utils.DialogUtils.RaiseAchievementDialog;
import static com.warsawcitygame.Utils.DialogUtils.RaiseDialogAbortMissionConfirmation;

public class CurrentMissionFragment extends Fragment
{
    Button abortMissionButton;
    Button mapButton;
    Button accomplishMissionButton;
    Dialog dialog;

    TextView missionDesc;
    TextView missionName;
    ImageView missionImage;
    TextView missionExp;
    double currentMissionX;
    double currentmissionY;
    double currentX;
    double currentY;
    LocationManager locationManager;
    LocationListener locationListener;

    public CurrentMissionFragment(){}

    private Location currentBestLocation = null;

    @Inject
    MissionsService service;

    @Inject
    SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_current_mission, container, false);
        abortMissionButton = ButterKnife.findById(rootView, R.id.abort_mission_button);
        mapButton = ButterKnife.findById(rootView, R.id.map_button);
        accomplishMissionButton = ButterKnife.findById(rootView, R.id.accomplishMissionButton);
        missionDesc = ButterKnife.findById(rootView,R.id.missionDesc);
        missionName = ButterKnife.findById(rootView,R.id.missionName);
        missionImage = ButterKnife.findById(rootView,R.id.imageView2);
        missionExp=ButterKnife.findById(rootView,R.id.missionExp);
        abortMissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog confirmationDialog = RaiseDialogAbortMissionConfirmation(getActivity(), getActivity(), new AbortMissionAction());
                confirmationDialog.show();
            }
        });

        accomplishMissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accomplishCurrentMission();
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

        locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);


        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                makeUseOfNewLocation(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }catch(SecurityException s) {
        }


        checkForCurrentMission();
        return rootView;
    }

    void makeUseOfNewLocation(Location location)
    {
        currentX = location.getLongitude();
        currentY = location.getLatitude();
    }

    private double measure(double lat1,double lon1,double lat2,double lon2){
        double R = 6378.137; // Radius of earth in KM
        double dLat = (lat2 - lat1) * Math.PI / 180;
        double dLon = (lon2 - lon1) * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return d * 1000; // meters
    }


    private void showLoadingDialog(){
        dialog = DialogUtils.RaiseDialogLoading(getActivity(), false);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        };
        Thread thread = new Thread(runnable);
        thread.run();
    }

    private boolean checkLocation()
    {
        double res = measure(currentY,currentX,currentMissionX,currentmissionY);
        if(res < 100)
            return true;
        else
            return false;
    }

    private void accomplishCurrentMission()
    {
        if(!checkLocation()) {
            DialogUtils.RaiseDialogShowError(getActivity(), "Error", "You must go to destination!");
            return;
        }
        Call<ResponseBody> call = service.AccomplishCurrentMission();
        showLoadingDialog();
        call.enqueue(new CustomCallback<ResponseBody>(getActivity())
        {
            @Override
            public void onSuccess(ResponseBody model)
            {
            }

            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit)
            {
                if(response!=null) {
                    int c = response.code();
                    if (response.isSuccess()) {
                        RaiseAchievementDialog("Well Done !", getActivity());
                        showBlank();
                    } else
                        DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error ");
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });
    }

    private void checkForCurrentMission()
    {

        Call<MissionModel> call = service.GetCurrentMission();
        showLoadingDialog();
        call.enqueue(new CustomCallback<MissionModel>(getActivity()) {
            @Override
            public void onSuccess(MissionModel model) {
            }

            @Override
            public void onResponse(Response<MissionModel> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    if(response.body() != null) {
                        updateLocation(response.body());
                        updateView(response.body());
                    }
                    else showBlank();
                }
               else
                {
                    DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Cannot load current mission");
                }
                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                if (dialog != null) {
                    dialog.dismiss();
                }
                super.onFailure(t);
            }
        });
    }

    private void updateLocation(MissionModel model)
    {
        this.currentMissionX = model.PlaceX;
        this.currentmissionY = model.PlaceY;

    }

    private void updateView(MissionModel model)
    {
        this.missionDesc.setText(model.MissionDescription.toString());
        this.missionName.setText(model.MissionName.toString());
        this.missionExp.setText(model.ExpReward+ " EXP REWARD");
        byte[] imageAsBytes= Base64.decode(model.Image.getBytes(), Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        this.missionImage.setImageBitmap(bm);
    }

    public void abortCurrentMission() {

        Call<ResponseBody> call = service.AbortCurrentMission();
        showLoadingDialog();
        call.enqueue(new CustomCallback<ResponseBody>(getActivity())
        {
            @Override
            public void onSuccess(ResponseBody model)
            {
                if (dialog != null) {
                    dialog.dismiss();
                }
                Toast toast = Toast.makeText(getActivity(), "Aborted !", Toast.LENGTH_LONG);
                toast.show();
                showBlank();
            }


            @Override
            public void onFailure(Throwable t)
            {
                if (dialog != null) {
                    dialog.dismiss();
                }
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });

    }

    private void showBlank()
    {
        Fragment fragment = new BlankCurrentMissionFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    private void showFilled()
    {
        Fragment fragment = new CurrentMissionFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    @Override public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    class AbortMissionAction implements DelegateAction {
        public void ExecuteAction(){
            abortCurrentMission();
        }
    }
}
