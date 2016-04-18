package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.warsawcitygame.R;


public class DonateFragment extends Fragment {

    public DonateFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_donate, container, false);
        ((ImageView)rootView.findViewById(R.id.augi)).setScaleType(ImageView.ScaleType.FIT_XY);
        Button button = (Button)rootView.findViewById(R.id.donateButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.twitchalerts.com/donate/overpow"));
                startActivity(viewIntent);
            }
        });
        return rootView;
    }
}