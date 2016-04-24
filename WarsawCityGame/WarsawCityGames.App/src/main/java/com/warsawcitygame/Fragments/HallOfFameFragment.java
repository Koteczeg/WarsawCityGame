package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.warsawcitygame.Adapters.ListViewAdapter;
import com.warsawcitygame.R;

import butterknife.ButterKnife;

public class HallOfFameFragment extends Fragment
{
    ListView ranking;
    ListViewAdapter adapter;
    String[] ranks;
    String[] levels;
    String[] levelsDescriptions;
    String[] names;
    int[] pics;

    public HallOfFameFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_hall_of_fame, container, false);
        ranking = ButterKnife.findById(rootView,R.id.listview);
        initializeRanking(rootView);
        return rootView;
    }

    private void initializeRanking(View rootView)
    {
        getData();
        setRankingLayout();
        adapter = new ListViewAdapter(rootView.getContext(), ranks, levels, levelsDescriptions, names, pics);
        ranking.setAdapter(adapter);
    }

    private void setRankingLayout()
    {
        int[] colors = {0, 0xFFFF0000, 0};
        ranking.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        ranking.setDividerHeight(2);
    }

    private void getData()
    {
        ranks = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };
        levels = new String[] { "5", "5", "5", "4", "4", "3", "3", "2", "1", "1" };
        levelsDescriptions = new String[] { "Siren", "Siren", "Siren", "Jar", "Jar", "Jar", "Jar", "Wars", "Newbie", "Newbie" };
        names = new String[] { "John","John","John","John","John","John","John","John","John","John" };
        pics = new int[] {
                R.drawable.tiger, R.drawable.arianagrande,
                R.drawable.adamlevine, R.drawable.tiger,
                R.drawable.arianagrande, R.drawable.adamlevine, R.drawable.tiger,
                R.drawable.adamlevine, R.drawable.arianagrande, R.drawable.arianagrande };
    }
}
