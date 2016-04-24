package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.warsawcitygame.R;

public class BlankCurrentMissionFragment extends Fragment {

	public BlankCurrentMissionFragment(){}

	@Override
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container,
            Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_blank_current_mission, container, false);

        return rootView;
    }







}
