package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygame.Activities.LoginActivity;
import com.warsawcitygame.Activities.MapsActivity;
import com.warsawcitygame.Adapters.MissionDto;
import com.warsawcitygame.Adapters.MissionHistoryAdapter;
import com.warsawcitygame.Adapters.RVAdapter;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DelegateAction;
import com.warsawcitygame.Utils.DelegateActionOneParam;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.MissionModel;
import com.warsawcitygames.models.UserMissionModel;
import com.warsawcitygamescommunication.Services.MissionsService;

import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class GetMissionFragment extends Fragment
{
    private List<MissionDto> missions;
    private RecyclerView rv;
    private List<MissionModel> missionsInfo;

    Dialog dialog;
    Button acceptMissionButton;
    Button missionLocationButton;
    @Inject
    MissionsService service;

    @Inject
    SharedPreferences preferences;

    public GetMissionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_get_mission, container, false);

        initializeData();

        final View row = inflater.inflate(R.layout.missions_row_view,container,false);
        rv=(RecyclerView)rootView.findViewById(R.id.rv);

        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        return rootView;
    }

    public class SetMissionCall implements DelegateActionOneParam
    {
        @Override
        public Boolean ExecuteAction(int id)
        {
            return setNewMission(id);
        }
    }

    private class SafeBoolean{
        public Boolean flag;
    }

    private Boolean setNewMission(int ind) {
        String username = preferences.getString(LoginActivity.USERNAME_KEY, null);
        //only for testing
        UserMissionModel model = new UserMissionModel(username,missions.get(ind).name);
        Call<ResponseBody> call = service.SetCurrentMission(model);
        showLoadingDialog();
        final SafeBoolean safeBoolean =new SafeBoolean();
        safeBoolean.flag=true;
        call.enqueue(new CustomCallback<ResponseBody>(getActivity())
        {
            @Override
            public void onSuccess(ResponseBody model)
            {
                Toast toast = Toast.makeText(getActivity(), "Current mission added !", Toast.LENGTH_LONG);
                toast.show();
                dialog.dismiss();
            }

            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit)
            {
                if (response.code() == 400)
                {
                    dialog.dismiss();
                    DialogUtils.RaiseDialogShowError(getActivity(), "Warning", "You already have a mission !");
                    safeBoolean.flag=false;
                } else
                {
                    super.onResponse(response, retrofit);
                }
                if (dialog != null)
                {
                    dialog.dismiss();
                }
            }


            @Override
            public void onFailure(Throwable t)
            {
                safeBoolean.flag=false;
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });
        return safeBoolean.flag;
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

    private void initializeData(){

        missions = new ArrayList<>();
        Call<List<MissionModel>> call = service.GetAllMissions();
        showLoadingDialog();
        call.enqueue(new CustomCallback<List<MissionModel>>(getActivity()) {
            @Override
            public void onSuccess(List<MissionModel> model) {
            }

            @Override
            public void onResponse(Response<List<MissionModel>> response, Retrofit retrofit) {

                if (response.isSuccess()) {
                    fillWithData(response.body());
                    initializeAdapter();
                } else {
                    DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Cannot load missions");
                }

                if (dialog != null) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });

    }

    private void fillWithData(List<MissionModel> body)
    {
        missions = new ArrayList<>();
        missionsInfo = new ArrayList<MissionModel>();

        MissionModel model;

        for(int i = 0; i< body.size(); i++)
        {
            model = body.get(i);
            missionsInfo.add(model);
            byte[] imageAsBytes=Base64.decode(model.Image.getBytes(), Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
            missions.add(new MissionDto(model.MissionName, model.MissionDescription, model.ExpReward, bm));
        }
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(missions, new SetMissionCall());
        rv.setAdapter(adapter);
    }
}
