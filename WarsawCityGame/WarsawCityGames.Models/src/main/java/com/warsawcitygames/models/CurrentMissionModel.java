package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

public class CurrentMissionModel
{
    @SerializedName("name")
    public String Name;

    @SerializedName("description")
    public String Description;

    @SerializedName("expReward")
    public String ExpReward;

    public CurrentMissionModel(String Name, String Description, String ExpReward)
    {
        this.Description = Description;
        this.ExpReward = ExpReward;
        this.Name = Name;
    }
}
