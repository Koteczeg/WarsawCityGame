package com.warsawcitygame.Adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.warsawcitygame.Fragments.FriendsFragment;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygames.models.friends_models.FriendModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FriendListViewAdapter extends BaseAdapter
{
    private Context context;
    private List<FriendModel> friends;
    private int layoutId;

    public FriendListViewAdapter(Context context, List<FriendModel> friends)
    {
        this.context = context;
        this.friends = friends;
        this.layoutId = R.layout.friend_list_item;
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(layoutId, parent, false);
        TextView name = (TextView) element.findViewById(R.id.friendName);
        CircleImageView friendAvatar = (CircleImageView)element.findViewById(R.id.friendAvatar);
        byte[] imageAsBytes = Base64.decode(friends.get(position).Image.getBytes(), Base64.DEFAULT);
        final Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        if(bm!=null)
        {
            friendAvatar.setImageBitmap(bm);
        }
        name.setText(friends.get(position).Name);
        element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogUtils.RaiseDialogShowProfile(context, bm, friends.get(position).Name, friends.get(position).Username, friends.get(position).ActionType);
            }
        });
        return element;
    }
}
