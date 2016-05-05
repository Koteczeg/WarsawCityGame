package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

public class UserMissionModel
{
    @SerializedName("UserName")
    public String UserName;

    @SerializedName("MissionName")
    public String MissionName;

    public UserMissionModel(String userName, String missionName)
    {
        this.UserName = userName;
        this.MissionName = missionName;
    }
}
