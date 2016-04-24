package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.warsawcitygame.Adapters.FriendListViewAdapter;
import com.warsawcitygame.R;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

public class FriendsFragment extends Fragment
{
    public FriendsFragment(){}
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        SearchView searchView = ButterKnife.findById(rootView, R.id.searchView);
        ListView friendsList = (ListView)rootView.findViewById(R.id.friendsList);
        prepareListView(rootView, friendsList);
        prepareSearchView(rootView, searchView);
        return rootView;
    }

    private void prepareSearchView(View rootView, SearchView searchView)
    {
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = ButterKnife.findById(searchView, id);
        editText.setTextColor(Color.BLACK);
        searchView.setBackgroundColor(0xFFC0E8FC);
        List<String> searchResults = new LinkedList<>();
        searchResults.add("Mateusz");
        FriendListViewAdapter searchResultsAdapter = new FriendListViewAdapter(rootView.getContext(), searchResults, this, R.layout.friend_search_result);
        ListView searchResultList = (ListView) rootView.findViewById(R.id.searchResults);
        searchResultList.setAdapter(searchResultsAdapter);
    }


    private void prepareListView(View rootView, ListView listView)
    {
        List<String> friendsNames = getFriends();
        FriendListViewAdapter friendsAdapter = new FriendListViewAdapter(rootView.getContext(), friendsNames, this);
        int[] colors = {0, 0xFF00BFFF, 0};
        listView.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
        listView.setDividerHeight(3);
        listView.setAdapter(friendsAdapter);
    }

    private List<String> getFriends()
    {
        List<String> friends = new LinkedList<>();
        for(int i=0; i<6; i++)
            friends.add("Dope");
        return friends;
    }

    public void onAddFriend()
    {
        Toast toast = Toast.makeText(getActivity(), "Friend added", Toast.LENGTH_SHORT);
        toast.show();
    }
}
