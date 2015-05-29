package com.example.foosball.app;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.foosball.app.model.TeamStat;
import com.example.foosball.app.ui.TeamStatRowView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TeamStatsListAdapter extends BaseAdapter {

  private final Context mContext;
  private List<TeamStat> mStats;
  private String mParentActivity;

  public TeamStatsListAdapter(Context context, String fragmentId) {
    mContext = context;
    mStats = new ArrayList<TeamStat>();
    mParentActivity = fragmentId;
  }

  @Override
  public int getCount() {
    return mStats.size();
  }

  @Override
  public Object getItem(int position) {
    return mStats.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = new TeamStatRowView(mContext);
    }

    TeamStatRowView view = (TeamStatRowView) convertView;
    view.bindData((TeamStat) getItem(position));
    return view;
  }

  public void addAll(List<TeamStat> list){
    mStats = new ArrayList<TeamStat>(list);
    notifyDataSetChanged();
  }

  public void sort(List<TeamStat> list) {
    Comparator<TeamStat> c = new Comparator<TeamStat>() {
      @Override
      public int compare(TeamStat lhs, TeamStat rhs) {
        return rhs.getRate() - lhs.getRate();
      }
    };
    for(int i = 0; i < list.size(); i++) {
      TeamStat stat = list.get(i);
      double wins = list.get(i).getWins();
      double losses = list.get(i).getLosses();
      Double d = wins/(wins+losses)*100;
      list.get(i).setRate(d.intValue());
    }
    Collections.sort(list, c);
  }

  public void setPlaces(List<TeamStat> list) {
    for(int i = 0; i < list.size(); i++){
      list.get(i).setPlace(i+1);
    }
  }

  public void clear() {
    mStats.clear();
  }
}
