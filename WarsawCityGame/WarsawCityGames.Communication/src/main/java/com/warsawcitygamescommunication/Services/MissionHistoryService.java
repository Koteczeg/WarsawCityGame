package com.warsawcitygamescommunication.Services;

import com.warsawcitygames.models.MissionHistoryModel;

import java.util.List;

/**
 * Created by bakala12 on 08.06.2016.
 */
public interface MissionHistoryService {
    List<MissionHistoryModel> getHistory();
}
