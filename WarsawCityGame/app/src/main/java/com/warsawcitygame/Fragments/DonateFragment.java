package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.scheme("https").authority("www.paypal.com").path("cgi-bin/webscr");
                uriBuilder.appendQueryParameter("cmd", "_donations");

                uriBuilder.appendQueryParameter("business", "duh555@o2.pl");
                uriBuilder.appendQueryParameter("lc", "US");
                uriBuilder.appendQueryParameter("item_name", "mPaypalItemName");
                uriBuilder.appendQueryParameter("no_note", "1");
                // uriBuilder.appendQueryParameter("no_note", "0");
                // uriBuilder.appendQueryParameter("cn", "Note to the developer");
                uriBuilder.appendQueryParameter("no_shipping", "1");
                uriBuilder.appendQueryParameter("currency_code", "EUR");
                Uri payPalUri = uriBuilder.build();

                    Log.d("debug", "Opening the browser with the url: " + payPalUri.toString());

                // Start your favorite browser
                try {
                    Intent viewIntent = new Intent(Intent.ACTION_VIEW, payPalUri);
                    startActivity(viewIntent);
                } catch (ActivityNotFoundException e) {
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getActivity(), "Something went wrong.", duration);
                    toast.show();
                }
            }
        });
        return rootView;
    }
}