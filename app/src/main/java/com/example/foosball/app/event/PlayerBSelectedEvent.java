package com.example.foosball.app.event;

import android.util.Log;

public class PlayerBSelectedEvent {
  private String mPlayerBName;
  private String mTeam;

  public PlayerBSelectedEvent(String playerName, String team) {
    this.mPlayerBName = playerName;
    this.mTeam = team;
  }

  public String getPlayerBNameFromEvent() {
    return mPlayerBName;
  }

  public String getTeam() { return mTeam; }
}
