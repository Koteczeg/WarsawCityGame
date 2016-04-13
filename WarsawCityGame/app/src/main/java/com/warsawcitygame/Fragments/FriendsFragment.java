package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SearchView;

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
        FriendListViewAdapter adapter = new FriendListViewAdapter(rootView.getContext(), friendsNames);
        ListView listView = (ListView)rootView.findViewById(R.id.friendsList);
        listView.setAdapter(adapter);

        List<String> searchResults = new LinkedList<String>();
        searchResults.add("Mateusz");
        searchResults.add("Mateusz");
        FriendListViewAdapter adapter1 = new FriendListViewAdapter(rootView.getContext(), searchResults, R.layout.friend_search_result );
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
}
