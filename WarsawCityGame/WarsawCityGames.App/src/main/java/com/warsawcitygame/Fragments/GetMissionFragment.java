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
import android.widget.TextView;

import com.dd.morphingbutton.MorphingButton;
import com.warsawcitygame.R;

import butterknife.ButterKnife;

public class GetMissionFragment extends Fragment
{
    ImageView placePic;
    TextView placeDescription;
    TextView placeExp;
    ImageView leftButton;
    ImageView rightButton;
    int[] placePicTab;
    String[] placeDescriptionTab;
    String[] placeExpTab;
    int i = 0;

    public GetMissionFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View rootView = inflater.inflate(R.layout.fragment_get_mission, container, false);
        initializeReferences(rootView);
        getData();
        UpdateView();

        final Animation fadeOutLeft = new AlphaAnimation(0f, 1f);
        final Animation fadeOutRight = new AlphaAnimation(0f, 1f);
        initializeAnimations(fadeOutLeft, fadeOutRight);
        setNavigationButtonsListeners(fadeOutLeft, fadeOutRight);

        final TextView missionButtonDescription =  (TextView)rootView.findViewById(R.id.missionacceptedId);
        missionButtonDescription.setVisibility(View.GONE);
        setAcceptMissionButton(rootView, missionButtonDescription);
        return rootView;
    }

    private void setAcceptMissionButton(View rootView, final TextView miss)
    {
        final MorphingButton btnMorph = ButterKnife.findById(rootView, R.id.acceptMissionButton);
        btnMorph.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                MorphingButton.Params circle = MorphingButton.Params.create()
                        .duration(500)
                        .cornerRadius((int) getResources().getDimension(R.dimen.mb_height_56))
                        .width((int) getResources().getDimension(R.dimen.mb_height_56))
                        .height((int) getResources().getDimension(R.dimen.mb_height_56))
                        .color(getResources().getColor(R.color.global_color_green_primary))
                        .colorPressed(getResources().getColor(R.color.global_color_green_primary))
                        .icon(R.drawable.little_checked);
                btnMorph.morph(circle);
                btnMorph.setClickable(false);
                miss.setVisibility(View.VISIBLE);
                leftButton.setVisibility(View.GONE);
                rightButton.setVisibility(View.GONE);
                return false;
            }
        });
    }

    private void initializeAnimations(Animation fadeOutLeft, Animation fadeOutRight)
    {
        fadeOutLeft.setDuration(300);
        fadeOutRight.setDuration(300);
        fadeOutLeft.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation)
            {
                leftButton.setVisibility(View.VISIBLE);
            }
        });
        fadeOutRight.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {}
            @Override
            public void onAnimationRepeat(Animation animation)
            {}
            @Override
            public void onAnimationEnd(Animation animation)
            {
                rightButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setNavigationButtonsListeners(final Animation leftButtonAnimation, final Animation rightButtonAnimation)
    {
        leftButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                i--;
                if (i == -1) {
                    i = placePicTab.length - 1;
                }
                leftButton.startAnimation(leftButtonAnimation);
                UpdateView();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
            i++;
                if (i == placePicTab.length) {
                    i = 0;
                }
                rightButton.startAnimation(rightButtonAnimation);
                UpdateView();
            }
        });
    }

    private void getData()
    {
        placePicTab = new int[]{R.drawable.ppolitechniki, R.drawable.warsawcentral, R.drawable.wisla};
        placeDescriptionTab = new String[]{"Visit main building of Warsaw University of Technology","Go to the Warsaw central station.", "Get to the Wisła river and try to catch some fish."};
        placeExpTab = new String[]{"EXP: 200", "EXP: 500", "EXP: 300"};
    }

    private void initializeReferences(View rootView)
    {
        placePic = ButterKnife.findById(rootView, R.id.placePic);
        placeDescription = ButterKnife.findById(rootView,R.id.placeDescription);
        placeExp = ButterKnife.findById(rootView,R.id.questExp);
        leftButton = ButterKnife.findById(rootView,R.id.lefftButton);
        rightButton = ButterKnife.findById(rootView,R.id.rightButton);
    }

    private void UpdateView() {
        placePic.setImageResource(placePicTab[i]);
        placeDescription.setText(placeDescriptionTab[i]);
        placeExp.setText(placeExpTab[i]);
    }
}
