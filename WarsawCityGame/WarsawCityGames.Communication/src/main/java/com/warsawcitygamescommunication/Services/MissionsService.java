package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.CurrentMissionModel;
import com.warsawcitygames.models.MissionModel;
import com.warsawcitygames.models.PlayerProfileDataModel;
import com.warsawcitygames.models.UserMissionModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface MissionsService
{
    @POST("Missions/SetCurrentMission")
    Call<ResponseBody> SetCurrentMission(@Body UserMissionModel userMissionModel);

    @POST("Missions/AbortCurrentMission")
    Call<ResponseBody> AbortCurrentMission(@Body UserMissionModel userMissionModel);

    @GET("Missions/GetCurrentMission")
    Call<CurrentMissionModel> GetCurrentMission();

    @POST("Missions/AccomplishCurrentMission")
    Call<ResponseBody> AccomplishCurrentMission(@Body UserMissionModel userMissionModel);

    @GET("Missions/GetAllMissions")
    Call<ResponseBody> GetAllMissions();
}
