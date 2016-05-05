package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

public class SetCurrentMissionModel
{
    @SerializedName("UserName")
    public String UserName;

    @SerializedName("MissionName")
    public String MissionName;

    public SetCurrentMissionModel(String userName, String missionName)
    {
        this.UserName = userName;
        this.MissionName = missionName;
    }
}
