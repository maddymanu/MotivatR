package com.adityabansal.motivatr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar.Model;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

/**
 * Created by adityabansal on 11/5/16.
 */

public class NavBarActivity extends AppCompatActivity {

    //publuc because we need them from another activity.
    public static FragmentStatePagerAdapter adapterViewPager;
    public static ViewPager viewPager;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        //  Declare a new thread to do a preference check
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                //  Initialize SharedPreferences
                SharedPreferences getPrefs = PreferenceManager
                        .getDefaultSharedPreferences(getBaseContext());

           /*     SharedPreferences.Editor editor = getPrefs.edit();
                editor.clear();
                editor.commit();*/

                //  Create a new boolean and preference and set it to true
                boolean isFirstStart = getPrefs.getBoolean("firstStart", true);

                //  If the activity has never started before...
                if (isFirstStart) {

                    //  Launch app intro
                    Intent i = new Intent(NavBarActivity.this, IntroActivity.class);
                    startActivity(i);

                    //  Make a new preferences editor
                    SharedPreferences.Editor e = getPrefs.edit();

                    //  Edit preference to make it false because we don't want this to run again
                    e.putBoolean("firstStart", false);

                    //  Apply changes
                    e.apply();
                }
            }
        });

        // Start the thread
        t.start();

        //setting the public variables
        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
//        viewPager.setCurrentItem(1);

        //Getting the bottom navigation bar
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);

        //2 models for the tab bar
        final ArrayList<Model> models = new ArrayList<>();


        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.new_f),
                        Color.parseColor("#0E74FA")
                ).title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.saved_ic),
                        Color.parseColor("#0E74FA")
                ).title("Cup")
                        .badgeTitle("with")
                        .build()
        );

        //setting the nav bar properties.
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setBgColor(Color.parseColor("#0DA5ED"));
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.deselect();
        navigationTabBar.setIconSizeFraction(Float.valueOf("0.5"));
    }

    //Custom Adapter to show the swipe view
    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private final int NUM_ITEMS = 2;
        private  MainSwipeView m;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show the feed view
                    if (m == null) {
                        m = new MainSwipeView();
                        return m;

                    } else {
                        return m;
                    }
                case 1: // Fragment # 1 - This will show the saved posts grudview
                    return new SavedPostsFragment();
//
                default:
                    return null;
            }
        }


        @Override
        public int getItemPosition(Object object) {
            // Causes adapter to reload all Fragments when
            // notifyDataSetChanged is called
            return POSITION_NONE;
        }


    }

    @Override
    public void onResume() {  // After a pause OR at startup
        super.onResume();
    }


}
