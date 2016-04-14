package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dd.morphingbutton.MorphingButton;
import com.warsawcitygame.R;

public class GetMissionFragment extends Fragment {

    public GetMissionFragment() {
    }

    ImageView placePic;
    TextView placeDescription;
    TextView placeExp;
    ImageView leftButton;
    ImageView rightButton;
    int[] placePicTab;
    String[] placeDescriptionTab;
    String[] placeExpTab;
    int i = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_get_mission, container, false);
        placePic = (ImageView) rootView.findViewById(R.id.placePic);
        placeDescription = (TextView) rootView.findViewById(R.id.placeDescription);
        placeExp = (TextView) rootView.findViewById(R.id.questExp);
        leftButton = (ImageView) rootView.findViewById(R.id.lefftButton);
        rightButton = (ImageView) rootView.findViewById(R.id.rightButton);
        placePicTab = new int[]{R.drawable.ppolitechniki, R.drawable.warsawcentral,
                R.drawable.wisla};
        placeDescriptionTab = new String[]{"Visit main building of Warsaw University of Technology","Go to the Warsaw central station.", "Get to the Wisła river and try to catch some fish."};
        placeExpTab = new String[]{"EXP: 200", "EXP: 500", "EXP: 300"};

        UpdateView();



        final Animation fadeOut = new AlphaAnimation(0f, 1f);
        final Animation fadeOut2 = new AlphaAnimation(0f, 1f);

        fadeOut.setDuration(1000);
        fadeOut2.setDuration(1000);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                leftButton.setVisibility(View.VISIBLE);

            }
        });
        fadeOut2.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                rightButton.setVisibility(View.VISIBLE);

            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i--;
                if (i == -1) {
                    i = placePicTab.length - 1;
                }
                leftButton.startAnimation(fadeOut);

                UpdateView();

            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            i++;
                if (i == placePicTab.length) {
                    i = 0;
                }
                rightButton.startAnimation(fadeOut2);

                UpdateView();
            }
        });
        final TextView miss=  (TextView)rootView.findViewById(R.id.missionacceptedId);
        miss.setVisibility(View.GONE);
        // sample demonstrate how to morph button to green circle with icon
        final MorphingButton btnMorph = (MorphingButton) rootView.findViewById(R.id.acceptMissionButton);

        btnMorph.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(500)
                        .cornerRadius((int) getResources().getDimension(R.dimen.mb_height_56)) // 60 dp
                        .width((int) getResources().getDimension(R.dimen.mb_height_56)) // 60 dp
                        .height((int) getResources().getDimension(R.dimen.mb_height_56)) // 60 dp
                        .color(getResources().getColor(R.color.global_color_green_primary)) // normal state color
                        .colorPressed(getResources().getColor(R.color.global_color_green_primary)) // pressed state color
                        .icon(R.drawable.checked); // icon
                btnMorph.morph(circle);
                btnMorph.setClickable(false);

                miss.setVisibility(View.VISIBLE);
                leftButton.setVisibility(View.GONE);
                rightButton.setVisibility(View.GONE);

                return false;
            }
        });
        return rootView;
    }

    private void UpdateView() {
        placePic.setImageResource(placePicTab[i]);
        placeDescription.setText(placeDescriptionTab[i]);
        placeExp.setText(placeExpTab[i]);
    }
}
