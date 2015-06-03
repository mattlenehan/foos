package com.example.foosball.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foosball.app.model.Game;
import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.ui.InputGameSubmitView;
import com.example.foosball.app.ui.KindHeaderView;
import com.example.foosball.app.ui.TeamScoreRowView;
import com.example.foosball.app.ui.UserProfileGraphView;
import com.example.foosball.app.ui.UserProfileTopRowView;
import com.example.foosball.app.ui.VsRowView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartGestureListener;

import com.github.mikephil.charting.data.LineDataSet;
import com.google.common.collect.Lists;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

/**
 * Created by mattlenehan on 5/31/15.
 */
public class UserProfileFragment extends Fragment {

  @Inject
  Bus mBus;

  private Activity mParentActivity;
  private String mFragmentId;

  private static final String STAT_EXTRA_KEY = "statExtraKey";

  private IndivStat mIndivStat;
  private String mName;
  private int mWins;
  private int mLosses;
  private int mRate;

  private List<Integer> mGameHistory;
  public LineChart mChart;

  public UserProfileTopRowView mUserProfileTopRow;
  public UserProfileGraphView mUserProfileGraph;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getActivity().getIntent();
    mName = intent.getStringExtra("name");
    mWins = intent.getIntExtra("wins", 0);
    mLosses = intent.getIntExtra("losses", 0);
    mRate = intent.getIntExtra("rate", 0);
    mGameHistory = Lists.newArrayList();

    FoosApplication.get().inject(this);
    mBus.register(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.user_profile_fragment, container, false);
    mFragmentId = UUID.randomUUID().toString();
    mUserProfileTopRow = (UserProfileTopRowView) view.findViewById(R.id.user_profile_top_row);

    mChart = (LineChart) view.findViewById(R.id.chart);
    mChart.setDrawGridBackground(false);
    mChart.setBackgroundColor(Color.WHITE);

    // no description text
    mChart.setDescription("");
    mChart.setNoDataTextDescription("Loading...");
    mChart.setDescriptionColor(Color.BLACK);
    mChart.setPadding(10, 10, 10 ,10);

    // enable touch gestures
    mChart.setTouchEnabled(false);

    mChart.setVisibleXRange(20);
    mChart.setVisibleYRange(20, YAxis.AxisDependency.LEFT);

    mChart.setScaleXEnabled(true);
    mChart.setScaleYEnabled(true);


    mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);

    YAxis leftAxis = mChart.getAxisLeft();
    leftAxis.setShowOnlyMinMax(true);
    leftAxis.setStartAtZero(false);


    mChart.getAxisRight().setEnabled(false);
    bindData();
    return view;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mBus.unregister(this);
  }

  public void bindData() {
    mUserProfileTopRow.bindData(mName, mWins, mLosses, mRate);
    fetchGameHistory();
  }

  public void fetchGameHistory() {
    ParseQuery<Game> q1 = ParseQuery.getQuery("Game");
    q1.whereEqualTo("TeamAPlayerA", mName);
    ParseQuery<Game> q2 = ParseQuery.getQuery("Game");
    q2.whereEqualTo("TeamAPlayerB", mName);
    ParseQuery<Game> q3 = ParseQuery.getQuery("Game");
    q3.whereEqualTo("TeamBPlayerA", mName);
    ParseQuery<Game> q4 = ParseQuery.getQuery("Game");
    q4.whereEqualTo("TeamBPlayerB", mName);

    List<ParseQuery<Game>> queries = new ArrayList<>();
    queries.add(q1);
    queries.add(q2);
    queries.add(q3);
    queries.add(q4);

    ParseQuery<Game> mainQuery = ParseQuery.or(queries);
    mainQuery.orderByAscending("createdAt");
    mainQuery.findInBackground(new FindCallback<Game>() {
      public void done(List<Game> games, ParseException e) {
        mGameHistory.add(0);
        for(Game g : games) {
          if(g.getTeamAPlayerA() != null && g.getTeamAPlayerA().equals(mName)){
            mGameHistory.add(g.getTeamAScore() - g.getTeamBScore());
          } else if(g.getTeamAPlayerB() != null && g.getTeamAPlayerB().equals(mName)){
            mGameHistory.add(g.getTeamAScore() - g.getTeamBScore());
          } else if(g.getTeamBPlayerA() != null && g.getTeamBPlayerA().equals(mName)){
            mGameHistory.add(g.getTeamBScore() - g.getTeamAScore());
          } else if(g.getTeamBPlayerB() != null && g.getTeamBPlayerB().equals(mName)){
            mGameHistory.add(g.getTeamBScore() - g.getTeamAScore());
          }
        }
        setData(mGameHistory.size(), 100);
      }
    });
  }


  private void setData(int count, int range) {

    ArrayList<String> xVals = new ArrayList<String>();
    for (int i = 0; i < count; i++) {
      xVals.add((i) + "");
    }

    ArrayList<Entry> yVals = new ArrayList<Entry>();
    int val = 0;
    for (int i = 0; i < count; i++) {
      val = val + mGameHistory.get(i);
      yVals.add(new Entry(val,i));
    }

    // create a dataset and give it a type
    LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
    set1.setFillAlpha(110);
    set1.setFillColor(Color.RED);

    // set the line to be drawn like this "- - - - - -"
    set1.setDrawValues(true);
    set1.setColor(Color.BLACK);
    set1.setLineWidth(3);
    set1.setCircleSize(0);
    set1.setDrawCircleHole(false);
    set1.setFillAlpha(65);
    set1.setFillColor(Color.GRAY);
    set1.setDrawFilled(true);

    ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
    dataSets.add(set1); // add the datasets

    // create a data object with the datasets
    LineData data = new LineData(xVals, dataSets);

    // set data
    mChart.setData(data);
  }
}
