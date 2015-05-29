package com.example.foosball.app.event;

import android.util.Log;

public class PlayerASelectedEvent {
  private String mPlayerAName;
  private String mTeam;

  public PlayerASelectedEvent(String playerName, String team) {
    this.mPlayerAName = playerName;
    this.mTeam = team;
  }

  public String getPlayerANameFromEvent() {
    return mPlayerAName;
  }

  public String getTeam() {
    return mTeam;
  }
}
