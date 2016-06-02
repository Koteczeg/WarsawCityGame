package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import com.warsawcitygame.Adapters.FriendListViewAdapter;
import com.warsawcitygame.Adapters.ListViewAdapter;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.friends_models.FriendModel;
import com.warsawcitygames.models.friends_models.RankingModel;
import com.warsawcitygamescommunication.Services.RankingService;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class HallOfFameFragment extends Fragment
{
    ListView ranking;
    ListViewAdapter adapter;
    String[] ranks;
    String[] levels;
    String[] levelsDescriptions;
    String[] names;
    int[] pics;
    private Dialog dialog;
    private List<RankingModel> rankingModels;

    @Inject
    RankingService service;

    public HallOfFameFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_hall_of_fame, container, false);
        ranking = ButterKnife.findById(rootView,R.id.listview);
        initializeRanking(rootView);
        return rootView;
    }

    private void initializeRanking(View rootView)
    {
        List<RankingModel> list = downloadData();
        setRankingLayout();
    }

    private void setRankingLayout()
    {
        int[] colors = {0, 0xFFFF0000, 0};
        ranking.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        ranking.setDividerHeight(2);
    }

    private List<RankingModel> downloadData()
    {
        showLoadingDialog();
        Call<List<RankingModel>> callData = service.GetPlayerRanking();
        callData.enqueue(new CustomCallback<List<RankingModel>>(getActivity()) {
            @Override
            public void onResponse(Response<List<RankingModel>> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    if (response.body() != null) {
                        rankingModels = response.body();
                    }
                }
                if (rankingModels == null) rankingModels = new LinkedList<>();
                adapter = new ListViewAdapter(getActivity(), rankingModels);
                ranking.setAdapter(adapter);
                setImages();
                hideDialog();
            }

            @Override
            public void onSuccess(List<RankingModel> model) {
            }

            @Override
            public void onFailure(Throwable t) {
                hideDialog();
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });
        return rankingModels;
    }

    private void setImages(){
        ImageView first = (ImageView)getView().findViewById(R.id.FirstPlace);
        ImageView second = (ImageView)getView().findViewById(R.id.SecondPlace);
        ImageView third = (ImageView)getView().findViewById(R.id.ThirdPlace);

        if(rankingModels.size()>=1)
        {
            Bitmap b1 = ListViewAdapter.convertPic(rankingModels.get(0).PlayerImage);
            if(b1!=null)
                first.setImageBitmap(b1);
            else
                first.setImageResource(R.drawable.default_image);
        }
        if(rankingModels.size()>=2)
        {
            Bitmap b1 = ListViewAdapter.convertPic(rankingModels.get(1).PlayerImage);
            if(b1!=null)
                second.setImageBitmap(b1);
            else
                second.setImageResource(R.drawable.default_image);
        }
        if(rankingModels.size()>=3)
        {
            Bitmap b1 = ListViewAdapter.convertPic(rankingModels.get(2).PlayerImage);
            if(b1!=null)
                third.setImageBitmap(b1);
            else
                third.setImageResource(R.drawable.default_image);
        }
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

    private void hideDialog()
    {
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }
}
