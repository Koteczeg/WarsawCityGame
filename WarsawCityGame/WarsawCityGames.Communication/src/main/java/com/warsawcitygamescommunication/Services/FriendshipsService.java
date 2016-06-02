package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
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
    Call<ResponseBody> AssignFriend(@Query("playerId") int playerId);
    @POST("Friendships/RemoveFriend")
    Call<ResponseBody> RemoveFriend(@Query("playerId") int playerId);
}
