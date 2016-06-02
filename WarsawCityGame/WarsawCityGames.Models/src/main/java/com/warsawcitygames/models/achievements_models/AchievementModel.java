package com.warsawcitygames.models.achievements_models;

import com.google.gson.annotations.SerializedName;

public class AchievementModel
{
    @SerializedName("name")
    public String Name;
    @SerializedName("description")
    public String Description;

    public AchievementModel(String name, String description)
    {
        this.Name=name;
        this.Description=description;
    }
}