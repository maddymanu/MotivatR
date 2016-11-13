package com.adityabansal.motivatr;

import android.graphics.Color;
import android.os.Bundle;
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
    public static FragmentStatePagerAdapter adapterViewPager;
    public static ViewPager viewPager;
    private FirebaseAnalytics mFirebaseAnalytics;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


        viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);

        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
//        viewPager.setCurrentItem(1);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<Model> models = new ArrayList<>();

/*        Model model;
        model = new Model();
        model.setTitle("Here some title to model");
        model.hideBadge();
        model.showBadge();
        model.toggleBadge();
        model.updateBadgeTitle("Here some title like NEW or some integer value");

        models.add(model);*/
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

//        navigationTabBar.setActiveColor(Color.RED);
        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);


        navigationTabBar.setBgColor(Color.parseColor("#0DA5ED"));
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.deselect();
        navigationTabBar.setIconSizeFraction(Float.valueOf("0.5"));
    }


    public class MyPagerAdapter extends FragmentStatePagerAdapter {
        private int NUM_ITEMS = 2;
        MainSwipeView m;

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
                case 0: // Fragment # 0 - This will show FirstFragment//                    m.adapter_mypager = adapterViewPager;
                    if (m == null) {
                        Log.d("CALLING POSITION 0", "NEW MAIN ACTIVITY");
                        m = new MainSwipeView();
                        return m;

                    } else {
                        return m;
                    }
                case 1: // Fragment # 0 - This will show FirstFragment
                    Log.d("CALLING POSITION 1", "NEW FRAGMENT");
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
        Log.d("NAV BAR", "ON RESUME");
    }

/*
    @Override
    public Parcelable saveState()
    {
        return null;
    }
*/

}
