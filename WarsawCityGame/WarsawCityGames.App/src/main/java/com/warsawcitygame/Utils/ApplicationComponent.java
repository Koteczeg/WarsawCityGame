package com.warsawcitygame.Utils;

import com.warsawcitygame.Activities.CropUserImageActivity;
import com.warsawcitygame.Activities.LoginActivity;
import com.warsawcitygame.Activities.MainActivity;
import com.warsawcitygame.Activities.MapsActivity;
import com.warsawcitygame.Activities.RegisterActivity;
import com.warsawcitygame.Activities.SplashActivity;
import com.warsawcitygame.Adapters.FriendListViewAdapter;
import com.warsawcitygame.Fragments.AchievementsFragment;
import com.warsawcitygame.Fragments.BlankCurrentMissionFragment;
import com.warsawcitygame.Fragments.CurrentMissionFragment;
import com.warsawcitygame.Fragments.DonateFragment;
import com.warsawcitygame.Fragments.FriendsFragment;
import com.warsawcitygame.Fragments.GetMissionFragment;
import com.warsawcitygame.Fragments.HallOfFameFragment;
import com.warsawcitygame.Fragments.LoadingFragment;
import com.warsawcitygame.Fragments.ProfileFragment;
import com.warsawcitygamescommunication.Infrastructure.ApplicationModule;
import com.warsawcitygamescommunication.Infrastructure.RestServicesModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {RestServicesModule.class, ApplicationModule.class})
public interface ApplicationComponent {
    void inject(CropUserImageActivity activity);
    void inject(LoginActivity activity);
    void inject(MainActivity activity);
    void inject(MapsActivity activity);
    void inject(RegisterActivity activity);
    void inject(SplashActivity activity);
    void inject(AchievementsFragment fragment);
    void inject(BlankCurrentMissionFragment fragment);
    void inject(CurrentMissionFragment fragment);
    void inject(DonateFragment fragment);
    void inject(FriendsFragment fragment);
    void inject(GetMissionFragment fragment);
    void inject(HallOfFameFragment fragment);
    void inject(LoadingFragment fragment);
    void inject(ProfileFragment fragment);
    void inject(FriendListViewAdapter adapter);
}
