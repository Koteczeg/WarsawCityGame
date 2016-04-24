package com.warsawcitygame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.warsawcitygame.R;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    String[] rank;
    String[] country;
    String[] country2;
    String[] population;
    int[] flag;
    LayoutInflater inflater;

    public ListViewAdapter(Context context, String[] rank, String[] country, String[] country2,
                           String[] population, int[] flag) {
        this.context = context;
        this.rank = rank;
        this.country = country;
        this.country2 = country2;
        this.population = population;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        return rank.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txtrank;
        TextView txtcountry;
        TextView txtcountry2;

        TextView txtpopulation;
        ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.listview_item, parent, false);

        // Locate the TextViews in listview_item.xml
        txtrank = (TextView) itemView.findViewById(R.id.rank);
        txtcountry = (TextView) itemView.findViewById(R.id.level);
        txtcountry2 = (TextView) itemView.findViewById(R.id.levelTxt);

        txtpopulation = (TextView) itemView.findViewById(R.id.name);
        // Locate the ImageView in listview_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.userpic);

        // Capture position and set to the TextViews
        txtrank.setText(rank[position]);
        txtcountry.setText(country[position]);
        txtcountry2.setText(country2[position]);

        txtpopulation.setText(population[position]);

        // Capture position and set to the ImageView
        imgflag.setImageResource(flag[position]);

        return itemView;
    }
}