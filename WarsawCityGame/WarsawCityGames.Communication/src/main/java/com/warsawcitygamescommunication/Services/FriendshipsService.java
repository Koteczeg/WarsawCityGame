package com.warsawcitygamescommunication.Services;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Query;

public interface FriendshipsService
{
    @POST("Friendships/GetFriends")
    Call<String> GetFriends();
    @POST("Friendships/FindFriend")
    Call<String> FindFriend(@Query("username") String username);
}
