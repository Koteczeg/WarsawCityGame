package com.warsawcitygame.Adapters;

import java.util.ArrayList;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.warsawcitygame.R;

public class GridViewAdapter extends BaseAdapter
{
    private ArrayList<String> listCountry;
    private ArrayList<Integer> listFlag;
    private Activity activity;

    public GridViewAdapter(Activity activity, ArrayList<String> listCountry, ArrayList<Integer> listFlag) {
        super();
        this.listCountry = listCountry;
        this.listFlag = listFlag;
        this.activity = activity;
    }

    @Override
    public int getCount()
    {
        return listCountry.size();
    }

    @Override
    public String getItem(int position)
    {
        return listCountry.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgViewFlag;
        public TextView txtViewTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder view;
        LayoutInflater inflator = activity.getLayoutInflater();
        if(convertView==null)
        {
            view = new ViewHolder();
            convertView = inflator.inflate(R.layout.gridview_row, null);
            view.txtViewTitle = (TextView) convertView.findViewById(R.id.achievement_title);
            view.imgViewFlag = (ImageView) convertView.findViewById(R.id.achievement_image);
            convertView.setTag(view);
        }
        else
        {
            view = (ViewHolder) convertView.getTag();
        }
        view.txtViewTitle.setText(listCountry.get(position));
        view.imgViewFlag.setImageResource(listFlag.get(position));
        return convertView;
    }
}
