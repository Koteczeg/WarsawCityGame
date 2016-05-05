package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.UserMissionModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.POST;

public interface MissionsService
{
    @POST("Mission/SetCurrentMission")
    Call<ResponseBody> SetCurrentMission(@Body UserMissionModel userMissionModel);

    @POST("Mission/AbortCurrentMission")
    Call<ResponseBody> AbortCurrentMission(@Body UserMissionModel userMissionModel);
}
