package com.example.foosball.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.ui.InputGameSubmitView;
import com.example.foosball.app.ui.KindHeaderView;
import com.example.foosball.app.ui.TeamScoreRowView;
import com.example.foosball.app.ui.UserProfileTopRowView;
import com.example.foosball.app.ui.VsRowView;
import com.squareup.otto.Bus;

import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by mattlenehan on 5/31/15.
 */
public class UserProfileFragment extends Fragment {

  @Inject
  Bus mBus;

  private Activity mParentActivity;
  private String mFragmentId;

  private static final String STAT_EXTRA_KEY = "statExtraKey";

  private IndivStat mIndivStat;
  private String mName;
  private int mWins;
  private int mLosses;
  private int mRate;

  public UserProfileTopRowView mUserProfileTopRow;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getActivity().getIntent();
    mName = intent.getStringExtra("name");
    mWins = intent.getIntExtra("wins", 0);
    mLosses = intent.getIntExtra("losses", 0);
    mRate = intent.getIntExtra("rate", 0);

    FoosApplication.get().inject(this);
    mBus.register(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
    mFragmentId = UUID.randomUUID().toString();
    mUserProfileTopRow = (UserProfileTopRowView) view.findViewById(R.id.user_profile_top_row);
    bindData();
    return view;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mBus.unregister(this);
  }

  public void bindData() {
    mUserProfileTopRow.bindData(mName, mWins, mLosses, mRate);
  }

}
