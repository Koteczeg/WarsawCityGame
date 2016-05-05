package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.SetCurrentMissionModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface MissionsService
{
    @POST("mission/setCurrentMission")
    Call<ResponseBody> set(@Body SetCurrentMissionModel setCurrentMissionModel);
}
