package com.example.foosball.app.model;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

/**
 * Created by mattlenehan on 3/28/15.
 */
@ParseClassName("TeamStat")
public class TeamStat extends ParseObject {

  private int rate;
  private String player1;
  private String player2;

  private int place;

  public TeamStat() {}

  public int getPlace() { return place; }

  public void setPlace(int place) {
    this.place = place;
  }

  public String getPlayer1() { return getString("player1"); }

  public void setPlayer1(String player1) { put("player1", player1); }

  public String getPlayer2() { return getString("player2"); }

  public void setPlayer2(String player1) { put("player2", player1); }

  public int getWins() {
    return getInt("wins");
  }

  public void setWins(int wins) {
    put("wins", wins);
  }

  public int getLosses() {
    return getInt("losses");
  }

  public void setLosses(int losses) {
    put("losses", losses);
  }

  public int getRate() {
    return rate;
  }

  public void setRate(int rate) {
    this.rate = rate;
  }

  public static void findInBackground(final FindCallback<TeamStat> callback) {
    ParseQuery<TeamStat> teamStatQuery = ParseQuery.getQuery(TeamStat.class);
    teamStatQuery.findInBackground(new FindCallback<TeamStat>() {
      @Override
      public void done(List<TeamStat> teamStats, ParseException e) {
        if(e == null) {
          callback.done(teamStats, null);
        } else {
          callback.done(null, e);
        }
      }
    });
  }
}
