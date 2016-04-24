package com.warsawcitygame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.warsawcitygame.R;

import butterknife.ButterKnife;

public class ListViewAdapter extends BaseAdapter
{

    Context context;
    //TODO we need it from database
    String[] ranks;
    String[] levels;
    String[] levelDescriptions;
    String[] names;
    //TODO Drawable pics
    int[] pics;


    public ListViewAdapter(Context context, String[] ranks, String[] levels, String[] levelDescriptions, String[] names, int[] flag)
    {
        this.context = context;
        this.ranks = ranks;
        this.levels = levels;
        this.levelDescriptions = levelDescriptions;
        this.names = names;
        this.pics = flag;
    }

    @Override
    public int getCount()
    {
        return ranks.length;
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        TextView rank = ButterKnife.findById(itemView, R.id.rank);
        TextView level = ButterKnife.findById(itemView, R.id.level);
        TextView levelDescription = ButterKnife.findById(itemView, R.id.levelTxt);
        TextView name = ButterKnife.findById(itemView, R.id.name);
        ImageView pic = ButterKnife.findById(itemView, R.id.userpic);

        rank.setText(ranks[position]);
        level.setText(levels[position]);
        levelDescription.setText(levelDescriptions[position]);
        name.setText(names[position]);
        pic.setImageResource(pics[position]);
        return itemView;
    }
}
