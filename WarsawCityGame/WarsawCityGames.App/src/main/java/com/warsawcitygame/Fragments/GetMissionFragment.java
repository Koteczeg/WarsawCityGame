package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.warsawcitygame.Adapters.MissionDto;
import com.warsawcitygame.Adapters.MissionHistoryAdapter;
import com.warsawcitygame.Adapters.RVAdapter;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygamescommunication.Services.MissionsService;

import java.util.ArrayList;

import java.util.List;

import javax.inject.Inject;

public class GetMissionFragment extends Fragment
{
    private List<MissionDto> persons;
    private RecyclerView rv;

    Dialog dialog;

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
        rv=(RecyclerView)rootView.findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
        return rootView;
    }

    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new MissionDto("Catch some fish", "Go to the Wisła river and try to catch some fish",200, R.drawable.default_image));
        persons.add(new MissionDto("Catch some fish", "Go to the Wisła river and try to catch some fish",400, R.drawable.default_image));
        persons.add(new MissionDto("Catch some fish", "Go to the Wisła river and try to catch some fish", 500, R.drawable.default_image));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(persons);
        rv.setAdapter(adapter);
    }
}
