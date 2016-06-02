package com.warsawcitygame.Fragments;

import android.app.Dialog;
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

import com.warsawcitygame.Adapters.FriendListViewAdapter;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.friends_models.FriendModel;
import com.warsawcitygamescommunication.Services.FriendshipsService;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class FriendsFragment extends Fragment
{
    @Inject FriendshipsService service;
    private SearchView searchView;
    private ListView friendsList;

    private List<FriendModel> friends = new LinkedList<>();
    List<FriendModel> searchResults = new LinkedList<>();

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
        loadFriends(rootView);
        return rootView;
    }

    private void initializeViews(final View rootView)
    {
        searchView = ButterKnife.findById(rootView, R.id.searchView);
        friendsList = ButterKnife.findById(rootView,R.id.friendsList);
        searchView.setBackgroundColor(0xFFC0E8FC);
        searchView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                searchFriend(rootView);
            }
        });
    }

    public void searchFriend(View rootView)
    {
        try
        {
            getSearchingResults(rootView);
        }
        catch(Exception e)
        {
            DialogUtils.RaiseDialogShowError(getActivity(), getActivity().getString(R.string.errorText), getActivity().getString(R.string.connectionErrorText));
        }
    }

    private List<FriendModel> getSearchingResults(final View rootView)
    {
        showLoadingDialog();
        Call<FriendModel> callData = service.FindFriend(searchView.getQuery().toString());
        callData.enqueue(new CustomCallback<FriendModel>(getActivity())
        {
            @Override
            public void onResponse(Response<FriendModel> response, Retrofit retrofit)
            {
                if (response.isSuccess())
                {
                    searchResults.clear();
                    hideDialog();
                    if(response.body()==null) return;
                    searchResults.add(response.body());

                } else
                {
                    searchResults.clear();
                    hideDialog();
                    return;
                }
                FriendListViewAdapter searchResultsAdapter = new FriendListViewAdapter(rootView.getContext(), friends, searchResults, service);
                ListView searchResultList = (ListView) rootView.findViewById(R.id.searchResults);
                searchResultList.setAdapter(searchResultsAdapter);
                hideDialog();
            }

            @Override
            public void onSuccess(FriendModel model)
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

    private void hideDialog()
    {
        if (dialog != null)
        {
            dialog.dismiss();
            dialog = null;
        }
    }


    private void loadFriends(View rootView)
    {
        try
        {
            getFriends(rootView);
        }
        catch(Exception e)
        {
            DialogUtils.RaiseDialogShowError(getActivity(), getActivity().getString(R.string.errorText), getActivity().getString(R.string.connectionErrorText));
        }
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
                FriendListViewAdapter friendsAdapter = new FriendListViewAdapter(rootView.getContext(), friends,searchResults, service);
                int[] colors = {0, 0xFF00BFFF, 0};
                friendsList.setDivider(new GradientDrawable(GradientDrawable.Orientation.RIGHT_LEFT, colors));
                friendsList.setDividerHeight(3);
                friendsList.setAdapter(friendsAdapter);
                hideDialog();
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
