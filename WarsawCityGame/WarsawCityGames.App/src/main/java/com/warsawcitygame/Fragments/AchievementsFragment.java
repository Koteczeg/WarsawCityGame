package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.warsawcitygame.Adapters.GridViewAdapter;
import com.warsawcitygame.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AchievementsFragment extends Fragment
{

    @Bind(R.id.ranking_gridview) GridView ranking;


	public AchievementsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
 
        View rootView = inflater.inflate(R.layout.fragment_achievements, container, false);
        ButterKnife.bind(this, rootView);

        //TODO we will get it from async task from main activity, look for 'pass list to fragment' in google, parcelable and so on
        ArrayList<String> descriptions= new ArrayList<>();
        ArrayList<Integer> pics= new ArrayList<>(); //its okay because achievements are static resources in application
        //TODO this method not here; async loading must be invoked in main activity so we can display loading fragment meanwhile
        prepareList(descriptions, pics);

        GridViewAdapter rankingAdapter= new GridViewAdapter(getActivity(),descriptions, pics);
        ranking.setAdapter(rankingAdapter);
        return rootView;
    }

    //TODO will be removed, async loading from parent activity will be invoked
    public void prepareList(final ArrayList<String> listCountry,final  ArrayList<Integer> listFlag)
    {

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
