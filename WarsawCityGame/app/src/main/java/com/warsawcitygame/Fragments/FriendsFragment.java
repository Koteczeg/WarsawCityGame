package com.warsawcitygame.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.warsawcitygame.Activities.MapsActivity;
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

        SearchView searchView = (SearchView) rootView.findViewById(R.id.searchView);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = (EditText) searchView.findViewById(id);
        editText.setText("Wyszukaj");
        editText.setTextColor(Color.BLACK);

        friendsNames = getFriends(null);
        FriendListViewAdapter adapter = new FriendListViewAdapter(rootView.getContext(), friendsNames, this);
        ListView listView = (ListView)rootView.findViewById(R.id.friendsList);
        listView.setAdapter(adapter);

        List<String> searchResults = new LinkedList<String>();
        searchResults.add("Mateusz");
        searchResults.add("Mateusz");
        FriendListViewAdapter adapter1 = new FriendListViewAdapter(rootView.getContext(), searchResults, this, R.layout.friend_search_result );
        ListView searchResultList = (ListView) rootView.findViewById(R.id.searchResults);
        searchResultList.setAdapter(adapter1);
        return rootView;
    }

    private List<String> getFriends(String username)
    {
        List<String> friends = new LinkedList<>();
        for(int i=0; i<6; i++)
            friends.add("Paweł");
        return friends;
    }

    public void onAddFriend(View view){
        Toast toast = Toast.makeText(getActivity(), "Friend added", Toast.LENGTH_SHORT);
        toast.show();
    }
}
