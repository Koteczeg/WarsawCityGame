package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.warsawcitygame.Adapters.ListViewAdapter;
import com.warsawcitygame.R;

public class HallOfFameFragment extends Fragment {
	
	public HallOfFameFragment(){}

    ListView list;
    ListViewAdapter adapter;
    String[] rank;
    String[] country;
    String[] country2;
    String[] population;
    int[] flag;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_hall_of_fame, container, false);
        // Generate sample data into string arrays
        rank = new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" };

        country = new String[] { "5", "5", "5",
                "4", "4", "3", "3", "2",
                "1", "1" };
        country2 = new String[] { "Siren", "Siren", "Siren",
                "Jar", "Jar", "Jar", "Jar", "Wars",
                "Newbie", "Newbie" };
        population = new String[] { "John Beer","John Beer","John Beer","John Beer","John Beer","John Beer","John Beer","John Beer","John Beer","John Beer" };

        flag = new int[] { R.drawable.tiger, R.drawable.arianagrande,
                R.drawable.adamlevine, R.drawable.tiger,
                R.drawable.arianagrande, R.drawable.adamlevine, R.drawable.tiger,
                R.drawable.adamlevine, R.drawable.arianagrande, R.drawable.arianagrande };
        // Locate the ListView in listview_main.xml
        list = (ListView)rootView.findViewById(R.id.listview);
        int[] colors = {0, 0xFFFF0000, 0}; // red for the example
        list.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        list.setDividerHeight(2);
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(rootView.getContext(), rank, country, country2, population, flag);
        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
        return rootView;
    }
}
