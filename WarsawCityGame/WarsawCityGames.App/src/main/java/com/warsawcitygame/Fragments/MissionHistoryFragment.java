package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygame.Adapters.MissionHistoryAdapter;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.MissionHistoryModel;
import com.warsawcitygamescommunication.Services.MissionHistoryService;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class MissionHistoryFragment extends android.app.Fragment{

    private MissionHistoryAdapter adapter;
    private RecyclerView listView;

    public MissionHistoryFragment() {
        // Required empty public constructor
    }

    @Inject
    MissionHistoryService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mission_history2, container, false);
        listView = (RecyclerView)view.findViewById(R.id.rv);
        final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        listView.setLayoutManager(llm);
        listView.setHasFixedSize(true);
        getData();
        return view;
    }

    protected void getData() {
        Call<List<MissionHistoryModel>> call = service.getHistory();
        final Dialog dialog = DialogUtils.RaiseDialogLoading(getActivity());
        call.enqueue(new CustomCallback<List<MissionHistoryModel>>(getActivity()) {
            @Override
            public void onSuccess(List<MissionHistoryModel> model) {
                dialog.dismiss();
            }

            @Override
            public void onFailure(Throwable t) {
                super.onFailure(t);
                dialog.dismiss();
            }

            @Override
            public void onResponse(Response<List<MissionHistoryModel>> response, Retrofit retrofit) {
                List<MissionHistoryModel> list = new LinkedList<>();
                if(response!=null){
                    int c = response.code();
                    dialog.dismiss();
                    if(response.isSuccess()){
                        if(response.body() != null){
                            for(MissionHistoryModel m: response.body()){
                                list.add(m);
                            }
                        }
                    }
                }
                adapter = new MissionHistoryAdapter(list);
                listView.setAdapter(adapter);
            }
        });
    }
}
