package com.warsawcitygame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.warsawcitygame.R;

import java.util.List;

/**
 * Created by bakala12 on 13.04.2016.
 */
public class FriendListViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> friends;
    private int layoutId;

    public FriendListViewAdapter(Context context, List<String> friends){
        this.context = context;
        this.friends = friends;
        this.layoutId = R.layout.friend_list_item;
    }

    public FriendListViewAdapter(Context context, List<String> friends, int layoutId){
        this(context, friends);
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(layoutId, parent, false);
        TextView friendName = (TextView) itemView.findViewById(R.id.friendName);
        friendName.setText(friends.get(position));
        return itemView;
    }
}
