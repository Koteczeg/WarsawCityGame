package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bakala12 on 04.05.2016.
 */
public class PlayerProfileDataModel {
    @SerializedName("Email")
    public String Email;
    @SerializedName("Name")
    public String Name;
    @SerializedName("Exp")
    public int Exp;
    @SerializedName("Level")
    public String Level;
    @SerializedName("Description")
    public String Description;
    @SerializedName("Username")
    public String Username;
    @SerializedName("UserImage")
    public byte[] UserImage;

    public PlayerProfileDataModel(String email, String name, int exp, String level, String username, byte[] userImage, String description) {
        Email = email;
        Name = name;
        Exp = exp;
        Level = level;
        Username = username;
        UserImage = userImage;
        Description = description;
    }
}
