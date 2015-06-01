package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.example.foosball.app.FoosApplication;
import com.example.foosball.app.R;
import com.example.foosball.app.model.IndivStat;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.squareup.otto.Bus;

import javax.inject.Inject;

/**
 * Created by mattlenehan on 5/31/15.
 */
public class UserProfileGraphView extends FrameLayout {

  @Inject
  Bus mBus;

  private Context mContext;

  private LineChart chart;

  public UserProfileGraphView(Context context) {
    this(context, null);
  }

  public UserProfileGraphView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public UserProfileGraphView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    FoosApplication.get().inject(this);
    mBus.register(this);
    mContext = context;
    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.user_profile_graph, this);
    chart = (LineChart) v.findViewById(R.id.chart);
  }

  public void bindData(IndivStat stat) {

    // fetch all games involving user

//    LineData lineData = LineData()
//    chart.setData();
  }
}
