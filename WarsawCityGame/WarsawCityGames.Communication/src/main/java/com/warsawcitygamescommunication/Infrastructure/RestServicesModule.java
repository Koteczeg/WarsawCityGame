package com.warsawcitygamescommunication.Infrastructure;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import com.warsawcitygamescommunication.Services.AccountService;
import com.warsawcitygamescommunication.Services.AchievementsService;
import com.warsawcitygamescommunication.Services.FriendshipsService;
import com.warsawcitygamescommunication.Services.MissionHistoryService;
import com.warsawcitygamescommunication.Services.MissionsService;
import com.warsawcitygamescommunication.Services.RankingService;
import com.warsawcitygamescommunication.Services.UserProfileService;

import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

@Module(
        includes = {ApplicationModule.class}
)
public class RestServicesModule {

    private String baseUrl;

    public RestServicesModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(final SharedPreferences preferences) {
        OkHttpClient client = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        client.interceptors().add(interceptor);
        client.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException
            {
                Request.Builder builder = chain.request().newBuilder();
                builder.addHeader("Accept-Language", Locale.getDefault().getLanguage());
                String token = preferences.getString("accessToken", null);
                if(!TextUtils.isEmpty(token)) {
                    builder.addHeader("Authorization", "Bearer " + token);
                }
                return chain.proceed(builder.build());
            }
        });
        client.setConnectTimeout(3, TimeUnit.MINUTES);
        client.setReadTimeout(3, TimeUnit.MINUTES);
        client.setWriteTimeout(3, TimeUnit.MINUTES);
        return client;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
    }



    @Provides
    @Singleton
    AccountService provideAccountService(Retrofit retrofit) {
        return retrofit.create(AccountService.class);
    }

    @Provides
    @Singleton
    MissionsService provideMissionService(Retrofit retrofit) {
        return retrofit.create(MissionsService.class);
    }

    @Provides
    @Singleton
    UserProfileService provideUserProfileService(Retrofit retrofit){
        return retrofit.create(UserProfileService.class);
    }

    @Provides
    @Singleton
    FriendshipsService provideFriendshipsService(Retrofit retrofit){
        return retrofit.create(FriendshipsService.class);
    }

    @Provides
    @Singleton
    AchievementsService provideAchievementsService(Retrofit retrofit)
    {
        return retrofit.create(AchievementsService.class);
    }

    @Provides
    @Singleton
    RankingService provideRankingService(Retrofit retrofit){
        return retrofit.create(RankingService.class);
    }

    @Provides
    @Singleton
    MissionHistoryService provideMissionHistoryService(Retrofit retrofit){
        return retrofit.create(MissionHistoryService.class);
    }
}
