package com.example.foosball.app;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by mattlenehan on 5/31/15.
 */
public class UserProfileActivity extends FragmentActivity {

  @Inject
  Bus mBus;

  private UserProfileFragment mUserProfileFragment;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FoosApplication.get().inject(this);
    mBus.register(this);

    setContentView(R.layout.user_profile_fragment);
    UserProfileFragment upf = new UserProfileFragment();
    upf.setArguments(savedInstanceState);

    FragmentManager fm = getFragmentManager();
    FragmentTransaction ft = fm.beginTransaction();
    ft.replace(android.R.id.content, upf);
    ft.commit();
  }

  public void onDestroy() {
    super.onDestroy();
    mBus.unregister(this);
  }

  @Override
  public void finish() {
    super.finish();
    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
  }

}
