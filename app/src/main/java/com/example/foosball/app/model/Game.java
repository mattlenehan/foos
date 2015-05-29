package com.example.foosball.app.model;

/**
 * Created by mattlenehan on 5/27/15.
 */

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("Game")
public class Game extends ParseObject {

  public String teamAPlayerA;
  public String teamAPlayerB;
  public int teamAScore;
  public String teamBPlayerA;
  public String teamBPlayerB;
  public int teamBScore;

  public Game(){}

  public Game(String teamAPlayerA,
              String teamAPlayerB,
              int teamAScore,
              String teamBPlayerA,
              String teamBPlayerB,
              int teamBScore) {
    this.teamAPlayerA = teamAPlayerA;
    this.teamAPlayerB = teamAPlayerB;
    this.teamAScore = teamAScore;
    this.teamBPlayerA = teamBPlayerA;
    this.teamBPlayerB = teamBPlayerB;
    this.teamBScore = teamBScore;
  }

  public String getTeamAPlayerA() {
    return getString("TeamAPlayerA");
  }

  public String getTeamAPlayerB() {
    return getString("TeamAPlayerB");
  }

  public String getTeamBPlayerA() {
    return getString("TeamBPlayerA");
  }

  public String getTeamBPlayerB() {
    return getString("TeamBPlayerB");
  }

  public int getTeamAScore() {
    return getInt("TeamAScore");
  }

  public int getTeamBScore() {
    return getInt("TeamBScore");
  }


}
