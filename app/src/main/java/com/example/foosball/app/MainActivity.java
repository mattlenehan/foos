package com.example.foosball.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.support.v4.view.ViewPager;
import foosball.tabswipe.adapter.TabsPagerAdapter;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.content.Intent;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.ui.SegmentedGroupView;
import com.parse.Parse;
import com.parse.ParseObject;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener{

  private ViewPager viewPager;
  private TabsPagerAdapter mAdapter;
  private ActionBar actionBar;
  private RadioButton mButton21;
  private RadioButton mButton22;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseObject.registerSubclass(IndivStat.class);
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
        Log.e("MATT", "onCheckedChanged now bro " + isChecked + "     - " + buttonView.toString());
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
      public void onPageSelected(int position) {}

      @Override
      public void onPageScrolled(int arg0, float arg1, int arg2) {}

      @Override
      public void onPageScrollStateChanged(int arg0) {
        if(viewPager.getCurrentItem() == 0) {
          mButton21.setChecked(true);
        } else {
          mButton22.setChecked(true);
        }
      }
    });
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
}

