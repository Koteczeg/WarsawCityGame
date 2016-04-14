package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
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
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_current_mission, container, false);

        Button button = (Button) rootView.findViewById(R.id.abortButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Fragment fragment = new BlankCurrentMissionFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
            }
        });
        return rootView;
    }







}
