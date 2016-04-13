package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.warsawcitygame.Adapters.FriendListViewAdapter;
import com.warsawcitygame.R;

import java.util.LinkedList;
import java.util.List;

public class FriendsFragment extends Fragment {
	
	public FriendsFragment(){}

    private List<String> friendsNames;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        friendsNames = getFriends(null);
        FriendListViewAdapter adapter = new FriendListViewAdapter(rootView.getContext(), friendsNames);
        ListView listView = (ListView)rootView.findViewById(R.id.friendsList);
        listView.setAdapter(adapter);
        return rootView;
    }

    private List<String> getFriends(String username)
    {
        List<String> friends = new LinkedList<>();
        for(int i=0; i<6; i++)
            friends.add("Paweł");
        return friends;
    }
}
