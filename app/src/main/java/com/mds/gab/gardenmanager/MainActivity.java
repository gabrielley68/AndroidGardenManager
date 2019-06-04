package com.mds.gab.gardenmanager;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mds.gab.gardenmanager.Fragments.CalendarFragment;
import com.mds.gab.gardenmanager.Fragments.ShopFragment;
import com.mds.gab.gardenmanager.Fragments.TaskFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    public ViewPager viewPager;
    private final ArrayList<Fragment> fragments = new ArrayList<>();
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        tabLayout.addOnTabSelectedListener(this);
        viewPager.addOnPageChangeListener(this);

        TaskFragment taskFragment = new TaskFragment();
        fragments.add(taskFragment);

        CalendarFragment calendarFragment = new CalendarFragment();
        fragments.add(calendarFragment);

        ShopFragment shopFragment = new ShopFragment();
        fragments.add(shopFragment);

        FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                currentPage = i;
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                switch(position){
                    case 0:
                        return "Task";
                    case 1:
                        return "Calendar";
                    case 2:
                        return "Shop";
                    default:
                        return "";
                }
            }
        };

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        for(Fragment fragment : getSupportFragmentManager().getFragments()){
            for(Fragment subFragment : fragment.getChildFragmentManager().getFragments()){
                if(subFragment.getFragmentManager() != null){
                    subFragment.getFragmentManager().popBackStack();
                }
            }
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
