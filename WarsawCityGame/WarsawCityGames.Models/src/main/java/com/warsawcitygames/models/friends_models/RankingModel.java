package com.warsawcitygames.models.friends_models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bakala12 on 02.06.2016.
 */

public class RankingModel {
    @SerializedName("PlayerLogin")
    public String PlayerLogin;
    @SerializedName("PlayerName")
    public String PlayerName;
    @SerializedName("PlayerExp")
    public int PlayerExp;
    @SerializedName("PlayerImage")
    public String PlayerImage;
    @SerializedName("LevelNumber")
    public int LevelNumber;
    @SerializedName("LevelName")
    public String LevelName;
}
