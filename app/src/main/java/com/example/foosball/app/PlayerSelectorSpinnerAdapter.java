package com.example.foosball.app;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;

import com.example.foosball.app.ui.TeamScoreRowView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectorSpinnerAdapter extends BaseAdapter implements SpinnerAdapter {

  private final Context mContext;
  private List<String> mOptions;
  private String mParentActivity;

  public PlayerSelectorSpinnerAdapter(Context context, String fragmentId) {
    mContext = context;
    mOptions = new ArrayList<String>();
    mParentActivity = fragmentId;
  }

  @Override
  public View getDropDownView(int position, View convertView, ViewGroup parent) {
    return null;
  }

  @Override
  public int getCount() {
    return mOptions.size();
  }

  @Override
  public Object getItem(int position) {
    return mOptions.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if(convertView == null) {
      convertView = new TeamScoreRowView(mContext);
    }

    TeamScoreRowView view = (TeamScoreRowView) convertView;
    view.bindData();

    return view;
  }


}
