package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.warsawcitygame.Adapters.GridViewAdapter;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.achievements_models.AchievementModel;
import com.warsawcitygamescommunication.Services.AchievementsService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class AchievementsFragment extends Fragment
{
    @Inject
    AchievementsService service;
    GridView ranking;
    private Dialog dialog;


    public AchievementsFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    private void showLoadingDialog()
    {
        dialog = DialogUtils.RaiseDialogLoading(getActivity(), false);
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                dialog.show();
            }
        };
        Thread thread = new Thread(runnable);
        thread.run();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View rootView = inflater.inflate(R.layout.fragment_achievements, container, false);
        ranking = ButterKnife.findById(rootView, R.id.ranking_gridview);

        showLoadingDialog();
        Call<List<AchievementModel>> callData = service.GetUserAchievements();
        callData.enqueue(new CustomCallback<List<AchievementModel>>(getActivity())
        {
            @Override
            public void onSuccess(List<AchievementModel> model)
            {
            }

            @Override
            public void onResponse(Response<List<AchievementModel>> response, Retrofit retrofit)
            {
                ArrayList<String> descriptions = new ArrayList<>();
                ArrayList<Integer> pics = new ArrayList<>();
                prepareList(descriptions, pics, response.body());
                GridViewAdapter rankingAdapter = new GridViewAdapter(getActivity(), descriptions, pics);
                ranking.setAdapter(rankingAdapter);
                if (dialog != null)
                {
                    dialog.dismiss();
                    dialog = null;
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });
        return rootView;
    }

    public void prepareList(final ArrayList<String> listCountry, final ArrayList<Integer> listFlag, List<AchievementModel> body)
    {
        int count=15;
        for(AchievementModel model : body){
            listCountry.add(model.Description);
            switch (model.Name)
            {
                case "loggedIn":
                    listFlag.add(R.drawable.loggedin);
                    break;
                case "accountCreated":
                    listFlag.add(R.drawable.acccreated);
                    break;
                default:
                    listFlag.add(R.drawable.dontknow);
                    break;
            }
            count--;
        }
        for(int i=0; i<count; i++)
        {
            listCountry.add("");
            listFlag.add(R.drawable.dontknow);
        }
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
