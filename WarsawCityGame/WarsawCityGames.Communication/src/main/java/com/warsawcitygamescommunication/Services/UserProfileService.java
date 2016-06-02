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
    @FormUrlEncoded
    Call<ResponseBody> ChangeUserData(
            @Field("Email") String Email,
            @Field("Name") String Name,
            @Field("Description") String Description);

    @POST("UserProfile/ChangePassword")
    Call<ResponseBody> ChangePassword(@Query("currentPassword") String currentPassword,
                                      @Query("newPassword") String newPassword);

    @GET("UserProfile/GetProfileData")
    Call<PlayerProfileDataModel> GetProfileData(@Query("username") String username);

    @POST("UserProfile/Upload")
    Call<ResponseBody> UpdateImage(@Body String file);

    @POST("UserProfile/RemoveImage")
    Call<ResponseBody> RemoveImage();

    @GET("UserProfile/GetUserImage")
    Call<String> GetUserImage();
}
