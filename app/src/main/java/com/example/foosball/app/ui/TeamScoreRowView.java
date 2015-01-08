package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.foosball.app.PlayerSelectorSpinnerAdapter;

import com.example.foosball.app.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.otto.Bus;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TeamScoreRowView extends FrameLayout {

  // subviews
  private Spinner mPlayerA;
  private Spinner mPlayerB;
  private IntegerView mScore;
  private ImageView mUp;
  private ImageView mDown;

  private List<String> mOptions = new ArrayList<String>();
  private Context mContext;

  private PlayerSelectorSpinnerAdapter mAdapter;

  public TeamScoreRowView(Context context) {
    this(context, null);
  }

  public TeamScoreRowView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TeamScoreRowView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    mContext = context;
    populatePlayers();
    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.team_score_row_view, this);
    mPlayerA = (Spinner) v.findViewById(R.id.team_a_player_1);
    mPlayerB = (Spinner) v.findViewById(R.id.team_a_player_2);
    mScore = (IntegerView) v.findViewById(R.id.team_a_score);
    mScore.setInt(0);
    mUp = (ImageView) v.findViewById(R.id.up_arrow);
    mDown = (ImageView) v.findViewById(R.id.down_arrow);
    bindData();
  }

  public void bindData() {
    mUp.setOnClickListener(new TeamScoreRowView.OnClickListener() {
      @Override
      public void onClick(View v) {
        int score = mScore.getInt() + 1;
        mScore.setInt(score);
      }
    });

    mDown.setOnClickListener(new ImageView.OnClickListener() {
      @Override
      public void onClick(View v) {
        if(mScore.getInt() != 0) {
          int score = mScore.getInt() - 1;
          mScore.setInt(score);
        }
      }
    });
  }

  public void populatePlayers() {
    ParseQuery<ParseObject> query = ParseQuery.getQuery("IndivStat");
    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> parseObjects, ParseException e) {
        if(e == null) {
          mOptions.add("");
          for(ParseObject stat : parseObjects) {
            StringBuilder name = new StringBuilder();
            name.append(stat.get("first"));
            name.append(" ");
            name.append(stat.get("last"));
            mOptions.add(name.toString());
          }
          ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(mContext, R.layout.spinner_item, mOptions);
          mPlayerA.setAdapter(spinnerArrayAdapter);
          mPlayerA.setPrompt("Choose Player 1");
          mPlayerB.setAdapter(spinnerArrayAdapter);
          mPlayerB.setPrompt("Choose Player 2 (Optional)");
        } else {
          Log.e("ERROR", "populating spinner");
        }
      }
    });
  }

}
