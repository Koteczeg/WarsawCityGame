package com.warsawcitygamescommunication.Services;

import com.warsawcitygames.models.friends_models.FriendModel;
import com.warsawcitygames.models.friends_models.RankingModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.POST;

/**
 * Created by bakala12 on 02.06.2016.
 */
public interface RankingService {
    @POST("Ranking/GetRanking")
    Call<List<RankingModel>> GetPlayerRanking();
}
