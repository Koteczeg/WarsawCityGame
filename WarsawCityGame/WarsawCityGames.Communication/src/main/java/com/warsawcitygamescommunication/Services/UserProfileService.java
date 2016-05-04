package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.PlayerProfileDataModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface UserProfileService {
    @POST("UserProfile/ChangeUserData")
    Call<ResponseBody> ChangeUserData(
            @Field("Username") String Username,
            @Field("Email") String Email,
            @Field("Name") String Name,
            @Field("Description") String Description,
            @Field("UserImage") byte[] UserImage);

    @POST("UserProfile/ChangePassword")
    Call<ResponseBody> ChangePassword(String username, String currentPassword, String newPassword);

    @GET("UserProfile/GetProfileData")
    Call<PlayerProfileDataModel> GetProfileData(@Query("username") String username);
}
