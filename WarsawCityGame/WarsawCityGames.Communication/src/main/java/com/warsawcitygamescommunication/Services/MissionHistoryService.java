package com.warsawcitygamescommunication.Services;

import com.warsawcitygames.models.MissionHistoryModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by bakala12 on 08.06.2016.
 */
public interface MissionHistoryService {
    @GET("History/GetHistory")
    Call<List<MissionHistoryModel>> getHistory();
}
