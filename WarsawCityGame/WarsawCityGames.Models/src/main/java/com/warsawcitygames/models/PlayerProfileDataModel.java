package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

public class PlayerProfileDataModel {
    @SerializedName("email")
    public String Email;
    @SerializedName("name")
    public String Name;
    @SerializedName("exp")
    public int Exp;
    @SerializedName("level")
    public String Level;
    @SerializedName("description")
    public String Description;
    @SerializedName("username")
    public String Username;

    public PlayerProfileDataModel(String email, String name, int exp, String level, String username, String description) {
        Email = email;
        Name = name;
        Exp = exp;
        Level = level;
        Username = username;
        Description = description;
    }
}
