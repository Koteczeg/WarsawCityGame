package com.warsawcitygame.Adapters;

import android.graphics.Bitmap;

public class MissionDto
{
    public String name;
    public String description;
    public int exp;
    //TODO
    public Bitmap photo;

    public MissionDto(String name, String age, int exp, Bitmap photo) {
        this.name = name;
        this.description = age;
        this.exp=exp;
        this.photo = photo;
    }
}
