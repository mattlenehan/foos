package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.foosball.app.FoosApplication;
import com.example.foosball.app.InputGameFragment;
import com.example.foosball.app.PlayerSelectorSpinnerAdapter;

import com.example.foosball.app.R;
import com.example.foosball.app.event.PlayerASelectedEvent;
import com.example.foosball.app.event.PlayerBSelectedEvent;
import com.example.foosball.app.event.ScoreChangeClickEvent;
import com.example.foosball.app.event.SubmitGameClickEvent;
import com.example.foosball.app.event.TurnInScoreSheetEvent;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class TeamScoreRowView extends FrameLayout {

  public static boolean flag = true;

  // subviews
  private TextView mTitle;
  private Spinner mPlayerA;
  private String mPlayerAName;
  private Spinner mPlayerB;
  private String mPlayerBName;
  private IntegerView mScore;
  private ImageView mUp;
  private ImageView mDown;
  private String mTeam;

  private List<String> mOptions = new ArrayList<String>();
  private Context mContext;

  private PlayerSelectorSpinnerAdapter mAdapter;

  @Inject
  Bus mBus;

  public TeamScoreRowView(Context context) {
    this(context, null);
  }

  public TeamScoreRowView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TeamScoreRowView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    if(flag){
      setTeam("A");
    } else {
      setTeam("B");
    }
    flag = !flag;

    FoosApplication.get().inject(this);
    mBus.register(this);
    mContext = context;
    populatePlayers();
    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.team_score_row_view, this);
    mTitle = (TextView) v.findViewById(R.id.team_score_title);
    mPlayerA = (Spinner) v.findViewById(R.id.team_a_player_1);
    mPlayerB = (Spinner) v.findViewById(R.id.team_a_player_2);
    mScore = (IntegerView) v.findViewById(R.id.team_a_score);
    mScore.setInt(0);
    mUp = (ImageView) v.findViewById(R.id.up_arrow);
    mDown = (ImageView) v.findViewById(R.id.down_arrow);

    mUp.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        int score = mScore.getInt() + 1;
        mScore.setInt(score);
        setScore(score);
        mBus.post(new ScoreChangeClickEvent(mScore, mTeam));
      }
    });

    mDown.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if(mScore.getInt() != 0) {
          int score = mScore.getInt() - 1;
          mScore.setInt(score);
          setScore(score);
          mBus.post(new ScoreChangeClickEvent(mScore, mTeam));
        }
      }
    });

    mPlayerA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPlayerAName = mOptions.get(position);
        setPlayerAName(mPlayerAName);
        mBus.post(new PlayerASelectedEvent(mPlayerAName, getTeam()));
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    mPlayerB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPlayerBName = mOptions.get(position);
        setPlayerBName(mPlayerBName);
        mBus.post(new PlayerBSelectedEvent(mPlayerBName, getTeam()));
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  public void bindData() {

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
          mPlayerB.setAdapter(spinnerArrayAdapter);
        } else {
          Log.e("ERROR", "populating spinner");
        }
      }
    });
  }

  public String getPlayerAName() { return mPlayerAName; }

  public void setPlayerAName(String name) {
    mPlayerAName = name;
  }

  public String getPlayerBName() {
    return mPlayerBName;
  }

  public void setPlayerBName(String name) {
    mPlayerBName = name;
  }

  public IntegerView getScore() {
    return mScore;
  }

  public void setScore(int score) { mScore.setInt(score); }

  public String getTeam() { return mTeam; }

  public void setTeam(String team) { mTeam = team; }

  @Subscribe
  public void handleSubmitGameEvent(SubmitGameClickEvent event) {
    Log.e("MATT", "handling the submit game click");
    mBus.post(new TurnInScoreSheetEvent(getTeam(), getPlayerAName(), getPlayerBName(), getScore()));
  }

}
