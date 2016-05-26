package com.warsawcitygame.Utils;

import android.app.Application;
import android.content.Context;

import com.warsawcitygamescommunication.Infrastructure.ApplicationModule;
import com.warsawcitygamescommunication.Infrastructure.RestServicesModule;

public class MyApplication extends Application
{

    private static String baseUrl = "http://warsawcitygames.azurewebsites.net/";

    private ApplicationComponent servicesComponent;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication.context = getApplicationContext();
        servicesComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .restServicesModule(new RestServicesModule(baseUrl))
                .build();
    }

    public ApplicationComponent getServicesComponent() {
        return servicesComponent;
    }

    public static Context getContext() {
        return MyApplication.context;
    }

}
