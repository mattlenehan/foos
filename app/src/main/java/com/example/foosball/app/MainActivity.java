package com.example.foosball.app;

import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import foosball.tabswipe.adapter.TabsPagerAdapter;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.foosball.app.model.Game;
import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.model.TeamStat;
import com.example.foosball.app.ui.KindHeaderView;
import com.example.foosball.app.ui.SegmentedGroupView;
import com.parse.Parse;
import com.parse.ParseObject;
import com.squareup.otto.Bus;

import java.util.UUID;

import javax.inject.Inject;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

  private ViewPager viewPager;
  private TabsPagerAdapter mAdapter;
  private ActionBar actionBar;
  private RadioButton mButton21;
  private RadioButton mButton22;
  private String mFragmentId;


  @Inject
  Bus mBus;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FoosApplication.get().inject(this);
    mBus.register(this);

    setContentView(R.layout.activity_main);

    getWindow().getDecorView().post(new Runnable() {
      @Override
      public void run() {
//        setupKindHeader();
      }
    });

    ParseObject.registerSubclass(IndivStat.class);
    ParseObject.registerSubclass(TeamStat.class);
    ParseObject.registerSubclass(Game.class);
    Parse.initialize(this, "QfCpVjpOTPyO8K1jnNubxcOKiRGy2WSxPsuzhogY", "ARo1wHY7iCA0S3HgmmxVQGDH7MbElUPxyfR6vN4H");

    // Initilization
    viewPager = (ViewPager) findViewById(R.id.pager);
    actionBar = getActionBar();
    actionBar.setCustomView(R.layout.segmented_control);
    actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_SHOW_CUSTOM);

    mButton21 = (RadioButton) actionBar.getCustomView().findViewById(R.id.button21);
    mButton22 = (RadioButton) actionBar.getCustomView().findViewById(R.id.button22);
    mButton21.setChecked(true);
    mButton21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
          viewPager.setCurrentItem(0);
        } else {
          viewPager.setCurrentItem(1);
        }
      }
    });

    mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(mAdapter);
    actionBar.setHomeButtonEnabled(false);

    /**
     * on swiping the viewpager make respective tab selected
     * */
    viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
      }

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {
      }

      @Override
      public void onPageScrollStateChanged(int arg0) {
        if (viewPager.getCurrentItem() == 0) {
          mButton21.setChecked(true);
        } else {
          mButton22.setChecked(true);
        }
      }
    });
  }

  @Override
  public void onResume() {
    super.onResume();
    IndivStatsListAdapter indivAdapter = new IndivStatsListAdapter(this, mFragmentId);
    TeamStatsListAdapter teamAdapter = new TeamStatsListAdapter(this, mFragmentId);

    indivAdapter.notifyDataSetChanged();
    teamAdapter.notifyDataSetChanged();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu main) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.main, main);

    return true;
  }

  public void inputGame(MenuItem item){
    Intent intent = new Intent(this, InputGameActivity.class);

    startActivity(intent);
    overridePendingTransition(R.anim.slide_up_from_bottom, R.anim.abc_fade_out);
  }

  @Override
  public void onCheckedChanged(RadioGroup group, int checkedId) {}

  public void setupKindHeader(){
    // Create an instance of some View that does the actual drawing of the line
    View customView = new KindHeaderView(this);

    // Figure out the window we have to work with
    Rect rect = new Rect();
    getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

    // Make sure the view is measured before doing this
    int requestedHeight = 5;

    // setup the params of the new view we'll attach
    WindowManager.LayoutParams wlp = new WindowManager.LayoutParams(
        rect.width(), requestedHeight,
        WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
            PixelFormat.TRANSLUCENT);
    // set the parameters so we fit on the bottom left of the window
    wlp.x = 0;
    wlp.y = 1134;
    wlp.gravity = Gravity.BOTTOM;

    // finally add it to the screen
    getWindowManager().addView(customView, wlp);
  }
}

