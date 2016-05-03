package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.AccessTokenModel;
import com.warsawcitygames.models.RegisterAccountModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface AccountService
{
    @POST("token")
    @FormUrlEncoded
    Call<AccessTokenModel> getToken(@Field("username") String username,
                                    @Field("password") String password,
                                    @Field("grant_type") String grantType);

    @POST("players/register")
    Call<ResponseBody> registerAccount(@Body RegisterAccountModel registerAccountModel);
}
