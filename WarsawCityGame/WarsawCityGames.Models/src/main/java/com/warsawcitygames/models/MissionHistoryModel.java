package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bakala12 on 08.06.2016.
 */
public class MissionHistoryModel {
    @SerializedName("missionId")
    public int missionId;
    @SerializedName("missionName")
    public String missionName;
    @SerializedName("missionDescription")
    public String missionDescription;
    @SerializedName("expReward")
    public int expReward;
    @SerializedName("placeName")
    public String placeName;
    @SerializedName("image")
    public String image;
    @SerializedName("userName")
    public String userName;
}
