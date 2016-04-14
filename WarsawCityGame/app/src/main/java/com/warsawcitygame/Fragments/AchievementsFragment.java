package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.warsawcitygame.Adapters.GridViewAdapter;
import com.warsawcitygame.R;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    private final String msg = "You have 2 new achievements !";
    private GridViewAdapter mAdapter;
    private ArrayList<String> listCountry;
    private ArrayList<Integer> listFlag;

    private GridView gridView;
	public AchievementsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_achievements, container, false);
        prepareList();

        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_popup);

        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // ((MainActivity)getActivity()).missionContext.isMissionAvailable = false;
        dialog.show();

        // prepared arraylist and passed it to the Adapter class
        mAdapter = new GridViewAdapter(getActivity(),listCountry, listFlag);

        // Set custom adapter to gridview
        gridView = (GridView)rootView.findViewById(R.id.gridView1);

        gridView.setAdapter(mAdapter);
        return rootView;
    }

    public void prepareList()
    {
        listCountry = new ArrayList<>();

        listCountry.add("Account created");
        listCountry.add("Logged in for a very first time");
        listCountry.add("");
        listCountry.add("");
        listCountry.add("");
        listCountry.add("");
        listCountry.add("");
        listCountry.add("");
        listCountry.add("");
        listCountry.add("");
        listCountry.add("");


        listFlag = new ArrayList<>();
        listFlag.add(R.drawable.acccreated);
        listFlag.add(R.drawable.loggedin);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);
        listFlag.add(R.drawable.dontknow);

    }
}
