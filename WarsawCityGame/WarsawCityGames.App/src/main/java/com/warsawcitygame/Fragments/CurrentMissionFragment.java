package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    public CurrentMissionFragment(){}

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

        mapButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

        checkForCurrentMission();
        return rootView;
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

    private void accomplishCurrentMission()
    {
        Call<ResponseBody> call = service.AccomplishCurrentMission();

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
                    if(response.body() != null)
                        updateView(response.body());
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

    private void updateView(MissionModel model)
    {
        this.missionDesc.setText(model.MissionDescription.toString());
        this.missionName.setText(model.MissionName.toString());

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
