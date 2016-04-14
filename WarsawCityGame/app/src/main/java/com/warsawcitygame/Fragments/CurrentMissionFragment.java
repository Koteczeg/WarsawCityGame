package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.warsawcitygame.Activities.MainActivity;
import com.warsawcitygame.Activities.MapsActivity;
import com.warsawcitygame.R;

import static android.content.DialogInterface.*;

public class CurrentMissionFragment extends Fragment {

    private String doneMsg = "Mission accomplished, Well Done !";
	public CurrentMissionFragment(){}
	
	@Override
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView;
       // if(true == this.getArguments().getBoolean("isMissionAvailable"))
            rootView = inflater.inflate(R.layout.fragment_current_mission, container, false);
      //  else
      //  {
           // rootView = inflater.inflate(R.layout.fragment_blank_current_mission, container, false);
      //  }

        Button button = (Button) rootView.findViewById(R.id.abortButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showBlank();
            }
        });

        Button mapButton = (Button) rootView.findViewById(R.id.button2);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                startActivity(intent);
            }
        });

        final Button doneButton = (Button) rootView.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_popup);

                TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
                text.setText(doneMsg);

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        showBlank();
                    }
                });

               // ((MainActivity)getActivity()).missionContext.isMissionAvailable = false;
                dialog.show();
            }
        });
        return rootView;
    }

    private void showBlank()
    {
        Fragment fragment = new BlankCurrentMissionFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();
    }
}
