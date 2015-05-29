package com.example.foosball.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.parse.ParseObject;
import com.squareup.otto.Bus;

import javax.inject.Inject;


public class InputGameActivity extends FragmentActivity {

  @Inject
  Bus mBus;

  private InputGameFragment mInputGameFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FoosApplication.get().inject(this);
    mBus.register(this);

    setContentView(R.layout.input_game_fragment);

    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(android.R.id.content, new InputGameFragment());
    ft.commit();
  }

  public void onDestroy() {
    super.onDestroy();
    mBus.unregister(this);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    return true;
    // Inflate the menu; this adds items to the action bar if it is present.
//    getMenuInflater().inflate(R.menu.input_game, menu);
//    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_cancel) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.abc_fade_in, R.anim.slide_down_from_top);
  }

  public void finish(MenuItem item) {
    this.finish();
  }


}
