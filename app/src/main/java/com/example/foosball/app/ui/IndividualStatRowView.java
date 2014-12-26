package com.example.foosball.app.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import android.graphics.Typeface;

import com.example.foosball.app.R;
import com.example.foosball.app.model.IndivStat;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.lang.Double;
import java.lang.Integer;

public class IndividualStatRowView extends FrameLayout {

  //sub views
  private ParseImageView mIcon;
  private TextView mFirstName;
  private TextView mLastName;
  private TextView mWins;
  private TextView mLosses;
  private TextView mRate;

  public IndividualStatRowView(Context context) {
      this(context, null);
  }

  public IndividualStatRowView(Context context, AttributeSet attrs) {
      this(context, attrs, 0);
  }

  public IndividualStatRowView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.individual_stat_row, this);
    mIcon      = (ParseImageView) v.findViewById(R.id.indiv_player_icon);
    mFirstName = (TextView) v.findViewById(R.id.indiv_player_first);
    mLastName  = (TextView) v.findViewById(R.id.indiv_player_last);
    mWins      = (TextView) v.findViewById(R.id.indiv_player_wins);
    mLosses    = (TextView) v.findViewById(R.id.indiv_player_losses);
    mRate      = (TextView) v.findViewById(R.id.indiv_player_rate);
  }

  public void bindData(IndivStat stat) {
//    mIcon.setParseFile(stat.getPhoto());
//    mIcon.loadInBackground(new GetDataCallback() {
//      @Override
//      public void done(byte[] bytes, ParseException e) {
//        if(e == null) {
//          mIcon.setVisibility(View.VISIBLE);
//          Log.e("PHOTO", "we have photo, bytes: " + bytes.length);
//        } else {
//          mIcon.setVisibility(View.GONE);
//          Log.e("ERROR", "photo crazyness " + bytes.length);
//        }
//      }
//    });
    mFirstName.setText(stat.getFirstName().toUpperCase());
    mFirstName.setTypeface(null, Typeface.BOLD);
    mLastName.setText(stat.getLastName());
    mWins.setText("" + stat.getWins());
    mLosses.setText(""+stat.getLosses());
    double wins = stat.getWins();
    double losses = stat.getLosses();
    Double d = wins/(wins+losses)*100;
    Integer winPercentage = d.intValue();
    mFirstName.setTextColor(findUsersColor(winPercentage));
    mRate.setText(""+winPercentage+"%");
    findUsersColor(winPercentage);
  }

  public int findUsersColor(int percent) {
    Double r = 255.0;
    Double g = 208.0;
    Double b = 19.0;
    r = r*percent/100.0;
    g = g*percent/100.0;
    b = b*percent/100.0;
    Color c = new Color();
    return c.rgb(r.intValue(),g.intValue(),b.intValue());
  }
}
