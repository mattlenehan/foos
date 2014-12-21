package com.example.foosball.app.model;


import android.net.Uri;

import com.example.foosball.app.ui.IndividualStatRowView;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

@ParseClassName("IndivStat")
public class IndivStat extends ParseObject {

  private int rate;
  public ParseFile file;

  public IndivStat() {
      //default constructor required
  }

  public ParseFile getPhoto() {
    return getParseFile("photo");
  }

  public void setPhoto(ParseFile photo) {
    put("photo", photo);
  }

  public String getFirstName() {
      return getString("first");
  }

  public void setFirstName(String first) {
      put("first", first);
  }

  public String getLastName() {
      return getString("last");
  }

  public void setLastName(String last) {
      put("last", last);
  }

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

  public static void findInBackground(final FindCallback<IndivStat> callback) {
    ParseQuery<IndivStat> indivStatQuery = ParseQuery.getQuery(IndivStat.class);
    indivStatQuery.findInBackground(new FindCallback<IndivStat>() {
        @Override
        public void done(List<IndivStat> indivStats, ParseException e) {
      if(e == null) {
        callback.done(indivStats, null);
      } else {
        callback.done(null, e);
      }
        }
    });
  }
}
