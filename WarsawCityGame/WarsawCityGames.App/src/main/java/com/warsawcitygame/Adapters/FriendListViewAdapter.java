package com.warsawcitygame.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.warsawcitygame.Fragments.FriendsFragment;
import com.warsawcitygame.R;
import com.warsawcitygames.models.friends_models.FriendModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FriendListViewAdapter extends BaseAdapter
{
    private Context context;
    private List<FriendModel> friends;
    private int layoutId;
    private final FriendsFragment friendsFragment;

    public FriendListViewAdapter(Context context, List<FriendModel> friends, FriendsFragment friendsFragment)
    {
        this.context = context;
        this.friends = friends;
        this.layoutId = R.layout.friend_list_item;
        this.friendsFragment = friendsFragment;
    }

    public FriendListViewAdapter(Context context, List<FriendModel> friends, FriendsFragment friendsFragment, int layoutId)
    {
        this(context, friends, friendsFragment);
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
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View element = inflater.inflate(layoutId, parent, false);
        TextView name = (TextView) element.findViewById(R.id.friendName);
        CircleImageView friendAvatar = (CircleImageView)element.findViewById(R.id.friendAvatar);
        byte[] imageAsBytes = Base64.decode(friends.get(position).Image.getBytes(), Base64.DEFAULT);
        Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        if(bm!=null)
        {
            friendAvatar.setImageBitmap(bm);
        }
        name.setText(friends.get(position).Name);
        if(layoutId != R.layout.friend_list_item)
        {
            Button addButton = (Button)element.findViewById(R.id.addFriendButton);
            addButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    friendsFragment.onAddFriend();
                }
            });
        }
        return element;
    }
}
