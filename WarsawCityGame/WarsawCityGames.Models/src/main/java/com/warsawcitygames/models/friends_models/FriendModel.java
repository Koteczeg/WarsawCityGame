package com.warsawcitygames.models.friends_models;


import com.google.gson.annotations.SerializedName;

public class FriendModel
{
    @SerializedName("id")
    public int Id;

    @SerializedName("image")
    public String Image;

    @SerializedName("name")
    public String Name;

    @SerializedName("username")
    public String Username;

    @SerializedName("actionType")
    public String ActionType;

    public FriendModel(int id, String image, String name, String username, String actionType)
    {
        this.Id = id;
        this.Image = image;
        this.Name = name;
        this.Username = username;
        this.ActionType = actionType;
    }
}
