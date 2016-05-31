package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.Response;
import com.warsawcitygames.models.friends_models.FriendModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Query;

public interface FriendshipsService
{
    @POST("Friendships/GetFriends")
    Call<List<FriendModel>> GetFriends();
    @POST("Friendships/FindFriend")
    Call<FriendModel> FindFriend(@Query("username") String username);
    @POST("Friendships/AssignFriend")
    Call<Response> AssignFriend(@Query("playerId") int playerId);
    @POST("Friendships/RemoveFriend")
    Call<Response> RemoveFriend(@Query("playerId") int playerId);
}
