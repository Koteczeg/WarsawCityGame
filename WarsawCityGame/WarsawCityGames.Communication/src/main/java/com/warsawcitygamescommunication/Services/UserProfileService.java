package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.PlayerProfileDataModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;

public interface UserProfileService {
    @POST("UserProfile/ChangeUserData")
    @FormUrlEncoded
    Call<ResponseBody> ChangeUserData(
            @Field("Username") String Username,
            @Field("Email") String Email,
            @Field("Name") String Name,
            @Field("Description") String Description);

    @POST("UserProfile/ChangePassword")
    Call<ResponseBody> ChangePassword(@Query("username") String username,
                                      @Query("currentPassword") String currentPassword,
                                      @Query("newPassword") String newPassword);

    @GET("UserProfile/GetProfileData")
    Call<PlayerProfileDataModel> GetProfileData(@Query("username") String username);

    @POST("UserProfile/Upload")
    Call<ResponseBody> UpdateImage(@Body RequestBody file);

    @GET("UserProfile/GetUserImage")
    Call<ResponseBody> GetUserImage();
}
