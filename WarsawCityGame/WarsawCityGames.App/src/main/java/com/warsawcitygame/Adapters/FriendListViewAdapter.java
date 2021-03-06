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
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.friends_models.FriendModel;
import com.warsawcitygamescommunication.Services.FriendshipsService;

import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Retrofit;


public class FriendListViewAdapter extends BaseAdapter
{
    private Context context;
    private List<FriendModel> results;
    private int layoutId;
    FriendshipsService service;
    List<FriendModel> searchResults;
    public FriendListViewAdapter(Context context, List<FriendModel> results, FriendshipsService service)
    {
        this.service = service;
        this.context = context;
        this.results = results;
        this.layoutId = R.layout.friend_list_item;
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return results.get(position);
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
        byte[] imageAsBytes = Base64.decode(results.get(position).Image.getBytes(), Base64.DEFAULT);
        final Bitmap bm = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
        final FriendListViewAdapter adapter= this;
        if(bm!=null)
        {
            friendAvatar.setImageBitmap(bm);
        }
        name.setText(results.get(position).Name);
        element.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DialogUtils.RaiseDialogShowProfile(context, bm, results.get(position), service, results,searchResults, adapter);
            }
        });
        return element;
    }
}
