package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foosball.app.R;

public class TeamScoreRowView extends FrameLayout {

  // subviews
  private Spinner mPlayerA;
  private Spinner mPlayerB;
  private TextView mScore;

  public TeamScoreRowView(Context context) {
    this(context, null);
  }

  public TeamScoreRowView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TeamScoreRowView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.team_score_row_view, this);
    mPlayerA = (Spinner) v.findViewById(R.id.team_a_player_1);
    mPlayerB = (Spinner) v.findViewById(R.id.team_a_player_2);
    mScore = (TextView) v.findViewById(R.id.team_a_score);
  }



}
