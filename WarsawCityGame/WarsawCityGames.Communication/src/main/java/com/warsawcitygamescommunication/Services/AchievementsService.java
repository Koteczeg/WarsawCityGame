package com.warsawcitygamescommunication.Services;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygames.models.achievements_models.AchievementModel;
import com.warsawcitygames.models.friends_models.FriendModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.POST;
import retrofit.http.Query;

public interface AchievementsService
{
    @POST("Achievements/GetUserAchievements")
    Call<List<AchievementModel>> GetUserAchievements();
    @POST("Achievements/AssignAchievement")
    Call<ResponseBody> AssignAchievement(@Query("achievementName") String achievementName);
}
