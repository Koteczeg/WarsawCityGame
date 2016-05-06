package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
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
import com.warsawcitygames.models.PlayerProfileDataModel;
import com.warsawcitygames.models.UserMissionModel;
import com.warsawcitygamescommunication.Services.MissionsService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

import static com.warsawcitygame.Utils.DialogUtils.RaiseDialogAbortMissionConfirmation;

public class CurrentMissionFragment extends Fragment
{
    Button abortMissionButton;
    Button mapButton;
    Button accomplishMissionButton;

    TextView missionDesc;
    TextView missionName;

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
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_popup_information);
                TextView text = ButterKnife.findById(dialog, R.id.text_dialog);
                text.setText(R.string.missionAcomplishedMessage);
                Button dialogButton = ButterKnife.findById(dialog, R.id.btn_dialog);
                dialogButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        dialog.dismiss();
                        showBlank();
                    }
                });
                dialog.show();
            }
        });


        checkForCurrentMission();
        return rootView;
    }

   // @OnClick(R.id.abort_mission_button)
  //  public void abortMission()
  //  {

   // }

    private void checkForCurrentMission()
    {
        String username = preferences.getString(LoginActivity.USERNAME_KEY, null);
        //only for testing
        Call<CurrentMissionModel> call = service.GetCurrentMission(username);
        call.enqueue(new CustomCallback<CurrentMissionModel>(getActivity()) {
            @Override
            public void onSuccess(CurrentMissionModel model) {
            }

            @Override
            public void onResponse(Response<CurrentMissionModel> response, Retrofit retrofit) {

                if (response.code() == 200) {
                    updateView(response.body());
                }
                if (response.code() == 400) {
                    showBlank();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });
    }

    private void updateView(CurrentMissionModel model)
    {
        this.missionDesc.setText(model.Description.toString());
        this.missionName.setText(model.Name.toString());
    }

    public void abortCurrentMission() {

        String username = preferences.getString(LoginActivity.USERNAME_KEY, null);
        //only for testing
        UserMissionModel model = new UserMissionModel(username, "");
        Call<ResponseBody> call = service.AbortCurrentMission(model);
        call.enqueue(new CustomCallback<ResponseBody>(getActivity())
        {
            @Override
            public void onSuccess(ResponseBody model)
            {
                Toast toast = Toast.makeText(getActivity(), "Aborted !", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });

    }

  //  @OnClick(R.id.acomplish_mission_button)
  //  public void accomplishMission()
 //   {

   // }

    @OnClick(R.id.map_button)
    public void showMap()
    {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);
    }

    private void showBlank()
    {
        Fragment fragment = new BlankCurrentMissionFragment();
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
           // showBlank();
        }
    }
}
