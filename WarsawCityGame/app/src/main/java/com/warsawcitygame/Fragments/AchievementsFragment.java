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

public class AchievementsFragment extends Fragment {

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
        listCountry.add("Jak Wenus i Mars jesteśmy z innych gwiazd");
        listCountry.add("Estoy cantando pero mi nombre es Pablito");
        listCountry.add("Traveled over 10 kilometers");
        listCountry.add("Fala akustyczna przemieszcza się w powietrzu w postaci lokalnych zagęszczeń i rozrzedzeń ośrodka");
        listCountry.add("Sunday morning rain is fallin, steal some covers share some skin");
        listCountry.add("I've got the move like Jagger, I've got the mooooove like Jagger");
        listCountry.add("Can you feel the love tonight");
        listCountry.add("Jak Wenus i Mars jesteśmy z innych gwiazd");
        listCountry.add("Jak Wenus i Mars jesteśmy z innych gwiazd");


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
