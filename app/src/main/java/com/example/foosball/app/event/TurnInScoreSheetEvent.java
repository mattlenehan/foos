package com.example.foosball.app.event;

import android.util.Log;

import com.example.foosball.app.ui.IntegerView;

/**
 * Created by mattlenehan on 4/6/15.
 */
public class TurnInScoreSheetEvent {
  private String mPlayerAName;
  private String mPlayerBName;
  private String mTeam;
  private IntegerView mScore;

  public TurnInScoreSheetEvent(String team, String playerAName, String playerBName, IntegerView score) {
    this.mPlayerAName = playerAName;
    this.mPlayerBName = playerBName;
    this.mTeam = team;
    this.mScore = score;
  }

  public String getPlayerANameFromEvent() {
    return mPlayerAName;
  }

  public String getPlayerBNameFromEvent() {
    return mPlayerBName;
  }

  public String getTeam() { return mTeam; }

  public IntegerView getScore() { return mScore; }
}
