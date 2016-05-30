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

    public FriendModel(int id, String image, String name)
    {
        this.Id = id;
        this.Image = image;
        this.Name = name;
    }
}
