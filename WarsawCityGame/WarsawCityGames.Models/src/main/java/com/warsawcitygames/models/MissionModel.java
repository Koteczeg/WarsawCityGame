package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

public class MissionModel
{
    @SerializedName("name")
    public String Name;
    @SerializedName("missionId")
    public int MissionId;
    @SerializedName("missionName")
    public String MissionName;
    @SerializedName("missionDescription")
    public String MissionDescription;
    @SerializedName("minimalLevelName")
    public String MinimalLevelName;
    @SerializedName("minimalLevelNumber")
    public int MinimalLevelNumber;
    @SerializedName("expReward")
    public int ExpReward;
    @SerializedName("placeName")
    public String PlaceName;
    @SerializedName("placeX")
    public double PlaceX;
    @SerializedName("placeY")
    public double PlaceY;
    @SerializedName("image")
    public String Image;
    @SerializedName("userName")
    public String UserName;

    public MissionModel(String name, int missionId, String missionName, String missionDescription,
                        String minimalLevelName, int minimalLevelNumber, int expReward, String placeName,
                        double placeX, double placeY, String image, String userName) {
        Name = name;
        MissionId = missionId;
        MissionName = missionName;
        MissionDescription = missionDescription;
        MinimalLevelName = minimalLevelName;
        MinimalLevelNumber = minimalLevelNumber;
        ExpReward = expReward;
        PlaceName = placeName;
        PlaceX = placeX;
        PlaceY = placeY;
        Image = image;
        UserName = userName;
    }
}
