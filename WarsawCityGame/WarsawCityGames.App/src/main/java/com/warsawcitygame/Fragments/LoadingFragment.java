package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warsawcitygame.R;

public class LoadingFragment extends Fragment {

    public LoadingFragment(){}

    @Override
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container,
                              Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_progress, container, false);

        return rootView;
    }







}