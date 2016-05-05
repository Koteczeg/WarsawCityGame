package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

public class CurrentMissionModel
{
    @SerializedName("Name")
    public String Name;

    @SerializedName("Description")
    public String Description;

    @SerializedName("ExpReward")
    public String ExpReward;

    public CurrentMissionModel(String Name, String Description, String ExpReward)
    {
        this.Description = Description;
        this.ExpReward = ExpReward;
        this.Name = Name;
    }
}
