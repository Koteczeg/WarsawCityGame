package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.warsawcitygame.Activities.MapsActivity;
import com.warsawcitygame.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.warsawcitygame.Utils.DialogUtils.RaiseDialogAbortMissionConfirmation;

public class CurrentMissionFragment extends Fragment
{
    Button abortMissionButton;
    Button mapButton;
    Button acomplishMissionButton;

    public CurrentMissionFragment(){}
	
	@Override
    public View onCreateView( final LayoutInflater inflater,final ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_current_mission, container, false);
        abortMissionButton = ButterKnife.findById(rootView, R.id.abort_mission_button);
        mapButton = ButterKnife.findById(rootView, R.id.map_button);
        acomplishMissionButton = ButterKnife.findById(rootView, R.id.acomplish_mission_button);
        return rootView;
    }

    @OnClick(R.id.abort_mission_button)
    public void abortMission()
    {
        Dialog confirmationDialog = RaiseDialogAbortMissionConfirmation(getActivity(), getActivity());
        confirmationDialog.show();
        //TODO bind methods inside dialog on cancel and confirmation
    }

    @OnClick(R.id.acomplish_mission_button)
    public void acomplishMission()
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_popup_information);
        TextView text = ButterKnife.findById(dialog, R.id.text_dialog);
        text.setText(R.string.missionAcomplishedMessage);
        Button dialogButton = ButterKnife.findById(dialog, R.id.btn_dialog);
        dialogButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                showBlank();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.map_button)
    public void showMap()
    {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);
    }

    private void showBlank()
    {
        Fragment fragment = new BlankCurrentMissionFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    @Override public void onDestroyView()
    {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
