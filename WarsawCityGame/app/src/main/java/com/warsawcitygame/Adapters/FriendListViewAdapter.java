package com.warsawcitygame.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.warsawcitygame.R;

import java.util.List;

/**
 * Created by bakala12 on 13.04.2016.
 */
public class FriendListViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> friends;

    public FriendListViewAdapter(Context context, List<String> friends){
        this.context = context;
        this.friends = friends;
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

        View itemView = inflater.inflate(R.layout.friend_list_item, parent, false);
        TextView friendName = (TextView) itemView.findViewById(R.id.friendName);
        friendName.setText(friends.get(position));
        return itemView;
    }
}
