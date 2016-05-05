package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.CurrentMissionModel;
import com.warsawcitygames.models.PlayerProfileDataModel;
import com.warsawcitygames.models.UserMissionModel;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MissionsService
{
    @POST("Mission/SetCurrentMission")
    Call<ResponseBody> SetCurrentMission(@Body UserMissionModel userMissionModel);

    @POST("Mission/AbortCurrentMission")
    Call<ResponseBody> AbortCurrentMission(@Body UserMissionModel userMissionModel);

    @GET("Mission/GetCurrentMission")
    Call<CurrentMissionModel> GetCurrentMission(@Query("username") String username);
}
