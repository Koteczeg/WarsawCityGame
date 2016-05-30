package com.warsawcitygame.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.warsawcitygame.Adapters.FriendListViewAdapter;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.PlayerProfileDataModel;
import com.warsawcitygames.models.friends_models.FriendModel;
import com.warsawcitygamescommunication.Services.FriendshipsService;
import com.warsawcitygamescommunication.Services.UserProfileService;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class FriendsFragment extends Fragment
{
    @Inject
    FriendshipsService service;
    private SearchView searchView;
    private ListView friendsList;

    private List<FriendModel> friends = new LinkedList<>();
    private Dialog dialog;

    public FriendsFragment()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_friends, container, false);
        initializeViews(rootView);

        loadFriends(rootView, friendsList);
        return rootView;
    }

    private void initializeViews(View rootView)
    {
        searchView = ButterKnife.findById(rootView, R.id.searchView);
        friendsList = ButterKnife.findById(rootView,R.id.friendsList);
    }

    private void prepareSearchView(View rootView, SearchView searchView)
    {
//        editText.setTextColor(Color.BLACK);
//        searchView.setBackgroundColor(0xFFC0E8FC);
//        List<String> searchResults = new LinkedList<>();
//        searchResults.add("Mateusz");
//        FriendListViewAdapter searchResultsAdapter = new FriendListViewAdapter(rootView.getContext(), searchResults, this, R.layout.friend_search_result);
//        ListView searchResultList = (ListView) rootView.findViewById(R.id.searchResults);
//        searchResultList.setAdapter(searchResultsAdapter);
    }


    private void loadFriends(View rootView, ListView friendsList)
    {
        try
        {
            getFriends(rootView);
        }
        catch(Exception e)
        {
            DialogUtils.RaiseDialogShowError(getActivity(), "Error", getActivity().getString(R.string.connectionErrorText));
        }
    }

    public void onAddFriend()
    {
        Toast toast = Toast.makeText(getActivity(), "Friend added", Toast.LENGTH_SHORT);
        toast.show();
    }

    public List<FriendModel> getFriends(final View rootView)
    {
        showLoadingDialog();
        Call<List<FriendModel>> callData = service.GetFriends();
        callData.enqueue(new CustomCallback<List<FriendModel>>(getActivity())
        {
            @Override
            public void onResponse(Response<List<FriendModel>> response, Retrofit retrofit)
            {
                if (response.isSuccess())
                {
                    friends = response.body();
                } else
                {
                    friends = null;
                }
                FriendListViewAdapter friendsAdapter = new FriendListViewAdapter(rootView.getContext(), friends, (FriendsFragment)getTargetFragment());
                int[] colors = {0, 0xFF00BFFF, 0};
                friendsList.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
                friendsList.setDividerHeight(3);
                friendsList.setAdapter(friendsAdapter);
                if (dialog != null)
                {
                    dialog.dismiss();
                    dialog = null;
                }
            }

            @Override
            public void onSuccess(List<FriendModel> model)
            {
            }

            @Override
            public void onFailure(Throwable t)
            {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error " + t.getMessage());
                super.onFailure(t);
            }
        });
        return friends;
    }

    private void showLoadingDialog()
    {
        dialog = DialogUtils.RaiseDialogLoading(getActivity(), false);
        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                dialog.show();
            }
        };
        Thread thread = new Thread(runnable);
        thread.run();
    }
}
