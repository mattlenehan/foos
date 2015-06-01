package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.foosball.app.FoosApplication;
import com.example.foosball.app.R;
import com.example.foosball.app.model.IndivStat;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by mattlenehan on 5/31/15.
 */
public class UserProfileTopRowView extends FrameLayout {

  @Inject
  Bus mBus;

  private Context mContext;

  // subviews
  private TextView mPlayerName;
  private TextView mPlayerWins;
  private TextView mPlayerLosses;
  private TextView mPlayerRate;

  public UserProfileTopRowView(Context context) {
    this(context, null);
  }

  public UserProfileTopRowView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public UserProfileTopRowView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    FoosApplication.get().inject(this);
    mBus.register(this);
    mContext = context;
    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.user_profile_top_row, this);
    mPlayerName = (TextView) v.findViewById(R.id.player_name);
    mPlayerWins = (TextView) v.findViewById(R.id.player_wins);
    mPlayerLosses = (TextView) v.findViewById(R.id.player_losses);
    mPlayerRate = (TextView) v.findViewById(R.id.player_rate);
  }

  public void bindData(String name, int wins, int losses, int rate) {
    mPlayerName.setText(name);
    mPlayerWins.setText(wins + " wins");
    mPlayerLosses.setText(losses + " losses");
    mPlayerRate.setText(rate + "%");
  }
}
