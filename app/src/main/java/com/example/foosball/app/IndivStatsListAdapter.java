package com.example.foosball.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.ui.IndividualStatRowView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IndivStatsListAdapter extends BaseAdapter {

  private final Context mContext;
  private List<IndivStat> mStats;
  private String mParentActivity;

  public IndivStatsListAdapter(Context context, String fragmentId) {
    mContext = context;
    mStats = new ArrayList<IndivStat>();
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
       convertView = new IndividualStatRowView(mContext);
    }

    IndividualStatRowView view = (IndividualStatRowView) convertView;
    view.bindData((IndivStat) getItem(position));
    return view;
  }

  public void addAll(List<IndivStat> list){
    mStats = new ArrayList<IndivStat>(list);
    notifyDataSetChanged();
  }

  public void sort(List<IndivStat> list) {
    Comparator<IndivStat> c = new Comparator<IndivStat>() {
      @Override
      public int compare(IndivStat lhs, IndivStat rhs) {
        return rhs.getRate() - lhs.getRate();
      }
    };
    List<IndivStat> res = new ArrayList<IndivStat>();
    for(int i = 0; i < list.size(); i++) {
      double wins = list.get(i).getWins();
      double losses = list.get(i).getLosses();
      Double d = wins/(wins+losses)*100;
      list.get(i).setRate(d.intValue());
    }
    Collections.sort(list, c);
  }
}
