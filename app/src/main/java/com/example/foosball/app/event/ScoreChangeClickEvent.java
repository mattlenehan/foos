package com.example.foosball.app.event;

import com.example.foosball.app.ui.IntegerView;

public class ScoreChangeClickEvent {

  private IntegerView mScore;
  private String mTeam;

  public ScoreChangeClickEvent(IntegerView score, String team) {
    mScore = score;
    mTeam = team;
  }

  public IntegerView getScoreFromEvent() {
    return mScore;
  }

  public String getTeam() { return mTeam; }
}
