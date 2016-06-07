package com.warsawcitygames.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by bakala12 on 02.06.2016.
 */

public class RankingModel {
    @SerializedName("playerLogin")
    public String PlayerLogin;
    @SerializedName("playerName")
    public String PlayerName;
    @SerializedName("playerExp")
    public int PlayerExp;
    @SerializedName("playerImage")
    public String PlayerImage;
    @SerializedName("levelNumber")
    public int LevelNumber;
    @SerializedName("levelName")
    public String LevelName;
    @SerializedName("isCurrentPlayer")
    public boolean IsCurrentPlayer;
}
