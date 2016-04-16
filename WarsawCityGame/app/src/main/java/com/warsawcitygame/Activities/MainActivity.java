package com.warsawcitygame.Activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;

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

import java.util.ArrayList;

import static com.warsawcitygame.Transitions.Animations.fadeOutAnimationNewFragment;


public class MainActivity extends Activity {
        private DrawerLayout mDrawerLayout;
        private ActionBarDrawerToggle mDrawerToggle;

        private ListView mDrawerList;

        // slide menu items
        private String[] navMenuTitles;
        private TypedArray navMenuIcons;

        private ArrayList<NavDrawerItem> navDrawerItems;
        private NavDrawerListAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);

                // load slide menu items
                navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

                // nav drawer icons from resources
                navMenuIcons = getResources()
                        .obtainTypedArray(R.array.nav_drawer_icons);

                mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
                mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

                navDrawerItems = new ArrayList<>();

                // adding nav drawer items to array
                // Home
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
                // Find People
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
                // Photos
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
                // Communities, Will add a counter here
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), true, "3"));
                // Pages
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
                // What's hot, We  will add a counter here
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
                navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));

                // Recycle the typed array
                navMenuIcons.recycle();

                mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

                // setting the nav drawer list adapter
                adapter = new NavDrawerListAdapter(getApplicationContext(),
                        navDrawerItems);
                mDrawerList.setAdapter(adapter);

                // enabling action bar app icon and behaving it as toggle button
                //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                //getActionBar().setHomeButtonEnabled(true);
                mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                        R.drawable.logo, //nav menu toggle icon
                        R.string.app_name, // nav drawer open - description for accessibility
                        R.string.app_name // nav drawer close - description for accessibility
                ) {
                        public void onDrawerClosed(View view) {
                                //getActionBar().setTitle(mTitle);
                                // calling onPrepareOptionsMenu() to show action bar icons
                                invalidateOptionsMenu();
                        }

                        public void onDrawerOpened(View drawerView) {
                                //getActionBar().setTitle(mDrawerTitle);
                                // calling onPrepareOptionsMenu() to hide action bar icons
                                invalidateOptionsMenu();
                        }
                };
                mDrawerLayout.setDrawerListener(mDrawerToggle);

                if (savedInstanceState == null) {
                        // on first time display view for first nav item
                        displayView(0);
                }
        }

        /**
         * Slide menu item click listener
         * */
        private class SlideMenuClickListener implements
                ListView.OnItemClickListener {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position,
                                        long id) {
                        // display view for selected nav drawer item
                        displayView(position);
                }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getMenuInflater().inflate(R.menu.main, menu);
                return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
                // toggle nav drawer on selecting action bar app icon/title
                if (mDrawerToggle.onOptionsItemSelected(item)) {
                        return true;
                }
                // Handle action bar actions click
                switch (item.getItemId()) {
                        case R.id.action_settings:
                                return true;
                        default:
                                return super.onOptionsItemSelected(item);
                }
        }

        /* *
         * Called when invalidateOptionsMenu() is triggered
         */
        @Override
        public boolean onPrepareOptionsMenu(Menu menu) {
                // if nav drawer is opened, hide the action items
                boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
                menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
                return super.onPrepareOptionsMenu(menu);
        }
        Fragment fragment = null;

        /**
         * Diplaying fragment view for selected nav drawer list item
         * */
        private void displayView(int position) {
                // update the main content by replacing fragments

                final FrameLayout fl = (FrameLayout)findViewById(R.id.frame_container);
                fl.setVisibility(View.INVISIBLE);
                switch (position) {
                        case 0:
                                fragment = new CurrentMissionFragment();
                                fadeOutAnimationNewFragment(fl);

                                break;
                        case 1: {
                                fragment = new ProfileFragment();
                                fadeOutAnimationNewFragment(fl);
                                break;
                        }
                        case 2:
                                fragment = new FriendsFragment();
                                fadeOutAnimationNewFragment(fl);

                                break;
                        case 3:
                                fragment = new GetMissionFragment();
                                fadeOutAnimationNewFragment(fl);

                                break;
                        case 4:

                                fragment = new LoadingFragment();
                                fadeOutAnimationNewFragment(fl);
                            // update selected item and title, then close the drawer
                            //mDrawerList.setItemChecked(position, true);
                            //mDrawerList.setSelection(position);
                            //setTitle(navMenuTitles[position]);

//                            mDrawerLayout.closeDrawer(mDrawerList);
//                            new Thread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                            try {
//                                                Thread.sleep(5000);
//                                            } catch (InterruptedException e) {
//                                                    e.printStackTrace();
//                                            }
//                                            fragment = new AchievementsFragment();
//
//                                            fadeOutAnimationNewFragment(fl);
//                                            if (fragment != null) {
//                                                    FragmentManager fragmentManager = getFragmentManager();
//                                                    fragmentManager.beginTransaction()
//                                                            .replace(R.id.frame_container, fragment).commit();
//
//                                                    // update selected item and title, then close the drawer
//                                                    //mDrawerList.setItemChecked(position, true);
//                                                    //mDrawerList.setSelection(position);
//                                                    //setTitle(navMenuTitles[position]);
//                                                    mDrawerLayout.closeDrawer(mDrawerList);
//                                            } else {
//                                                    // error in creating fragment
//                                                    Log.e("MainActivity", "Error in creating fragment");
//                                            }
//                                    }
//                            }).start();

                                //FragmentManager fragmentManager = getFragmentManager();
                                //fragmentManager.beginTransaction()
                                //        .replace(R.id.frame_container, fragment).commit();

                                break;
                        case 5:
                                fragment = new HallOfFameFragment();
                                fadeOutAnimationNewFragment(fl);

                                break;
                        case 6:
                                // logging off logic async somehow
                                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                                startActivity(i);
                                finish();
                                break;
                        case 7:
                                fragment = new LoadingFragment();
                                fadeOutAnimationNewFragment(fl);
                                break;
                        default:
                                break;
                }

                if (fragment != null) {
                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_container, fragment).commit();

                        // update selected item and title, then close the drawer
                        mDrawerList.setItemChecked(position, true);
                        mDrawerList.setSelection(position);
                        setTitle(navMenuTitles[position]);
                        mDrawerLayout.closeDrawer(mDrawerList);
                } else {
                        // error in creating fragment
                        Log.e("MainActivity", "Error in creating fragment");
                }
        }


        /**
         * When using the ActionBarDrawerToggle, you must call it during
         * onPostCreate() and onConfigurationChanged()...
         */

        @Override
        protected void onPostCreate(Bundle savedInstanceState) {
                super.onPostCreate(savedInstanceState);
                // Sync the toggle state after onRestoreInstanceState has occurred.
                mDrawerToggle.syncState();
        }

        @Override
        public void onConfigurationChanged(Configuration newConfig) {
                super.onConfigurationChanged(newConfig);
                // Pass any configuration change to the drawer toggls
                mDrawerToggle.onConfigurationChanged(newConfig);
        }

}


