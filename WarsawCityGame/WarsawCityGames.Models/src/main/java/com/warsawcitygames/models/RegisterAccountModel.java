package com.warsawcitygames.models;

public class RegisterAccountModel
{
    private String userName;
    private String email;
    private String password;

    public RegisterAccountModel(String userName, String email, String password)
    {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
}
