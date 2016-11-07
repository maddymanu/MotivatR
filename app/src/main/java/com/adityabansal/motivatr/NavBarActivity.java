package com.adityabansal.motivatr;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar;
import com.gigamole.navigationtabbar.ntb.NavigationTabBar.Model;

import java.util.ArrayList;

/**
 * Created by adityabansal on 11/5/16.
 */

public class NavBarActivity extends AppCompatActivity {
    public static FragmentStatePagerAdapter adapterViewPager;
    public static ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar_main);



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
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor("#1D287F")
                ).title("Heart")
                        .badgeTitle("NTB")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_second),
                        Color.parseColor("#1D287F")
                ).title("Cup")
                        .badgeTitle("with")
                        .build()
        );


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 0);

  /*      navigationTabBar.setTitleMode(NavigationTabBar.TitleMode.ALL);
        navigationTabBar.set
        navigationTabBar.setBadgeGravity(NavigationTabBar.BadgeGravity.BOTTOM);
        navigationTabBar.setBadgePosition(NavigationTabBar.BadgePosition.CENTER);
        navigationTabBar.setTypeface("fonts/custom_font.ttf");
        navigationTabBar.setIsBadged(true);
        navigationTabBar.setIsTitled(false);
        navigationTabBar.setIsTinted(false);
        navigationTabBar.setIsBadgeUseTypeface(true);
        navigationTabBar.setBadgeBgColor(Color.RED);
        navigationTabBar.setBadgeTitleColor(Color.WHITE);
        navigationTabBar.setIsSwiped(true);
//        navigationTabBar.setBgColor(Color.BLACK);
        navigationTabBar.setBadgeSize(10);
        navigationTabBar.setTitleSize(10);*/

        navigationTabBar.setBgColor(Color.parseColor("#505CAF"));
        navigationTabBar.setIsSwiped(true);
        navigationTabBar.deselect();
        navigationTabBar.setIconSizeFraction(Float.valueOf("0.5"));
    }





    public  class MyPagerAdapter extends FragmentStatePagerAdapter {
        private  int NUM_ITEMS = 2;
        MainActivity m;

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
                    if(m == null){
                        Log.d("CALLING POSITION 0" , "NEW MAIN ACTIVITY");
                        m= new MainActivity();
                        return m;

                    }else {
                        return m;
                    }
                case 1: // Fragment # 0 - This will show FirstFragment
                    Log.d("CALLING POSITION 1" , "NEW FRAGMENT");
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
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();
        Log.d("NAV BAR" , "ON RESUME");
    }

/*
    @Override
    public Parcelable saveState()
    {
        return null;
    }
*/

}
