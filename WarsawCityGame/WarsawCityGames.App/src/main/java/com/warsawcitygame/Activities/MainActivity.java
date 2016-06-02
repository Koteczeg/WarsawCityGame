package com.warsawcitygame.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.ListView;

import com.warsawcitygame.Fragments.DonateFragment;
import com.warsawcitygame.Fragments.GetMissionFragment;
import com.warsawcitygame.Fragments.CurrentMissionFragment;
import com.warsawcitygame.Fragments.LoadingFragment;
import com.warsawcitygame.Fragments.ProfileFragment;
import com.warsawcitygame.Fragments.AchievementsFragment;
import com.warsawcitygame.Fragments.FriendsFragment;
import com.warsawcitygame.R;
import com.warsawcitygame.Fragments.HallOfFameFragment;
import com.warsawcitygame.Adapters.NavDrawerListAdapter;
import com.warsawcitygame.CustomControls.NavDrawerItem;
import com.warsawcitygame.Utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

import static com.warsawcitygame.Transitions.Animations.fadeOutAnimationNewFragment;

public class MainActivity extends Activity
{
    @Bind(R.id.menu_layout) DrawerLayout menuLayout;
    @Bind(R.id.menu_list) ListView menuList;
    @Bind(R.id.frame_container) FrameLayout frameLayout;
    @Inject SharedPreferences preferences;

    private ActionBarDrawerToggle menuToggle;
    private Fragment activeFragment = null;
    public static final String USER_LOGGED_IN_KEY = "userLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).getServicesComponent().inject(this);
        initializeMenu();
        if (savedInstanceState == null)
        {
            displayView(0);
        }
    }

    private void initializeMenu()
    {
        NavDrawerListAdapter menuAdapter = new NavDrawerListAdapter(this, getMenuElements());
        menuList.setAdapter(menuAdapter);
        menuToggle = new ActionBarDrawerToggle(this, menuLayout, R.drawable.logo, R.string.app_name, R.string.app_name)
        {
            public void onDrawerClosed(View view)
            {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView)
            {
                invalidateOptionsMenu();
            }
        };
        menuLayout.setDrawerListener(menuToggle);
    }

    private ArrayList<NavDrawerItem> getMenuElements()
    {
        ArrayList<NavDrawerItem> menuElements = new ArrayList<>();
        String[] menuTitles = getResources().getStringArray(R.array.menu_titles);
        TypedArray menuIcons = getResources().obtainTypedArray(R.array.menu_icons);
        for(int i=0; i<8; i++)
        {
            menuElements.add(new NavDrawerItem(menuTitles[i], menuIcons.getResourceId(i, -1)));
        }
        menuIcons.recycle();
        return menuElements;
    }

    @OnItemClick(R.id.menu_list)
    public void onMenuOptionClick(int position)
    {
        displayView(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (menuToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        boolean drawerOpen = menuLayout.isDrawerOpen(menuList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private void displayView(int position)
    {
        frameLayout.setVisibility(View.INVISIBLE);
        switch (position)
        {
            case 0:
                activeFragment = new CurrentMissionFragment();
                break;
            case 1:
                activeFragment = new ProfileFragment();
                break;
            case 2:
                activeFragment = new FriendsFragment();
                break;
            case 3:
                activeFragment = new GetMissionFragment();
                break;
            case 4:
                activeFragment = new AchievementsFragment();
                break;
            case 5:
                activeFragment = new HallOfFameFragment();
                break;
            case 6:
                logout();
                break;
            case 7:
                activeFragment = new DonateFragment();
                break;
        }
        replaceFragment(position);
        fadeOutAnimationNewFragment(frameLayout);
    }


    private void replaceFragment(int position)
    {
        if (activeFragment != null)
        {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, activeFragment).commit();
            menuList.setItemChecked(position, true);
            menuList.setSelection(position);
            menuLayout.closeDrawer(menuList);
        }
        else
        {
            Log.e("MainActivity", "Error in creating activeFragment");
        }
    }

    private void logout()
    {
        SharedPreferences.Editor edit = preferences.edit();
        edit.putBoolean(USER_LOGGED_IN_KEY, false);
        edit.apply();
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        menuToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        menuToggle.onConfigurationChanged(newConfig);
    }




}


