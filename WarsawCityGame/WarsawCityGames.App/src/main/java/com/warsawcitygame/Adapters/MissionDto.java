package com.warsawcitygame.Adapters;

public class MissionDto
{
    public String name;
    public String description;
    public int exp;
    //TODO
    public int photoId;

    public MissionDto(String name, String age, int exp, int photoId) {
        this.name = name;
        this.description = age;
        this.exp=exp;
        this.photoId = photoId;
    }
}
