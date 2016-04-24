package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.warsawcitygame.R;

public class BlankCurrentMissionFragment extends Fragment
{
	public BlankCurrentMissionFragment(){}

	@Override
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_blank_current_mission, container, false);
    }
}
