package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by bakala12 on 04.05.2016.
 */
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
    @FormUrlEncoded
    Call<ResponseBody> GetProfileData(String username);
}
