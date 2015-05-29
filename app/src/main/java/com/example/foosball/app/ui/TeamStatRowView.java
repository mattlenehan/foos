package com.example.foosball.app.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foosball.app.R;
import com.example.foosball.app.model.TeamStat;

public class TeamStatRowView extends FrameLayout {

  //sub views
  private TextView mPlace;
  private TextView mPlayerA;
  private TextView mPlayerB;
  private TextView mWins;
  private TextView mLosses;
  private TextView mRate;

  public TeamStatRowView(Context context) {
      this(context, null);
  }

  public TeamStatRowView(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
  }

  public TeamStatRowView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    LayoutInflater inflater = LayoutInflater.from(context);
    View v  = inflater.inflate(R.layout.team_stat_row, this);

    mPlace = (TextView) v.findViewById(R.id.team_place);
    mPlayerA  = (TextView) v.findViewById(R.id.team_player_a);
    mPlayerB  = (TextView) v.findViewById(R.id.team_player_b);
    mWins   = (TextView)  v.findViewById(R.id.team_wins);
    mLosses = (TextView)  v.findViewById(R.id.team_losses);
    mRate   = (TextView)  v.findViewById(R.id.team_rate);
  }

  public void bindData(TeamStat stat) {
    mPlace.setText(String.valueOf(stat.getPlace()) + ".");
    mPlayerA.setText(stat.getPlayer1() + " &");
    mPlayerB.setText(stat.getPlayer2());
    mWins.setText("" + stat.getWins());
    mLosses.setText(""+stat.getLosses());
    double wins = stat.getWins();
    double losses = stat.getLosses();
    Double d = wins/(wins+losses)*100;
    Integer winPercentage = d.intValue();
    mPlayerA.setTextColor(findUsersColor(winPercentage));
    mPlayerB.setTextColor(findUsersColor(winPercentage));
    mRate.setText(""+winPercentage);
    findUsersColor(winPercentage);
  }

  public int findUsersColor(int percent) {
    Double r = 200.0;
    Double g = 180.0;
    Double b = 19.0;
    r = r*percent/100.0;
    g = g*percent/100.0;
    b = b*percent/100.0;
    Color c = new Color();
    return c.rgb(r.intValue(),g.intValue(),b.intValue());
  }
}
