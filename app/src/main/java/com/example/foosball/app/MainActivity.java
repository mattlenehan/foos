package com.example.foosball.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import foosball.tabswipe.adapter.TabsPagerAdapter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuInflater;
import android.content.Intent;

import com.example.foosball.app.model.IndivStat;
import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = {"Individual", "Team"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseObject.registerSubclass(IndivStat.class);
        Parse.initialize(this, "QfCpVjpOTPyO8K1jnNubxcOKiRGy2WSxPsuzhogY", "ARo1wHY7iCA0S3HgmmxVQGDH7MbElUPxyfR6vN4H");

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        final int[] ICONS = new int[] {
                R.drawable.ic_action_name,
                R.drawable.ic_action_name2
        };

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (int i = 0; i<tabs.length;i++) {
            actionBar.addTab(actionBar.newTab()
                    .setIcon(getResources().getDrawable(ICONS[i]))
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        // on tab selected
        // show respected fragment view
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu main) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, main);

        return true;
    }

    public void inputGame(MenuItem item){
        System.out.println("Input Game called" + item.getItemId());
        Intent intent = new Intent(this, InputGameActivity.class);
        startActivity(intent);
    }
}

