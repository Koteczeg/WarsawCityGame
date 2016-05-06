package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

public class UserMissionModel
{
    @SerializedName("userName")
    public String UserName;

    @SerializedName("missionName")
    public String MissionName;

    public UserMissionModel(String userName, String missionName)
    {
        this.UserName = userName;
        this.MissionName = missionName;
    }
}
