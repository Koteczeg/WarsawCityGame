package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.warsawcitygame.Activities.MapsActivity;
import com.warsawcitygame.R;

import static android.content.DialogInterface.*;

public class LoadingFragment extends Fragment {

    public LoadingFragment(){}

    @Override
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container,
                              Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.progress_layout, container, false);

        return rootView;
    }







}
