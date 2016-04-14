package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.warsawcitygame.R;

import static android.content.DialogInterface.*;

public class CurrentMissionFragment extends Fragment {
	
	public CurrentMissionFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_current_mission, container, false);

        return rootView;
    }







}
