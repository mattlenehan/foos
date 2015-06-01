package com.example.foosball.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foosball.app.event.PlayerASelectedEvent;
import com.example.foosball.app.event.PlayerBSelectedEvent;
import com.example.foosball.app.event.ScoreChangeClickEvent;
import com.example.foosball.app.event.SubmitGameClickEvent;
import com.example.foosball.app.event.TurnInScoreSheetEvent;
import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.model.TeamStat;
import com.example.foosball.app.model.Game;
import com.example.foosball.app.ui.InputGameSubmitView;
import com.example.foosball.app.ui.IntegerView;
import com.example.foosball.app.ui.KindHeaderView;
import com.example.foosball.app.ui.TeamScoreRowView;
import com.example.foosball.app.ui.VsRowView;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import com.example.foosball.app.FoosApplication;
import com.example.foosball.app.FoosModule;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class InputGameFragment extends Fragment {

  private Activity mParentActivity;
  private String mFragmentId;
  private PlayerSelectorSpinnerAdapter mAdapter;

  private LinearLayout mView;
  private KindHeaderView mKindHeader;
  private TeamScoreRowView mTeamA;
  private TeamScoreRowView mTeamB;
  private VsRowView mVsRow;
  private InputGameSubmitView mSubmitButton;

  private IntegerView teamAScore;
  private IntegerView teamBScore;
  private String teamAPlayerAName;
  private String teamAPlayerBName;
  private String teamBPlayerAName;
  private String teamBPlayerBName;

  @Inject
  Bus mBus;

  public List<String> mOptions;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.e("NEW", "HERE create");
    super.onCreate(savedInstanceState);

    FoosApplication.get().inject(this);
    mBus.register(this);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Log.e("NEW", "HERE createView");
    View view = inflater.inflate(R.layout.input_game_fragment, container, false);
    mFragmentId = UUID.randomUUID().toString();
    mKindHeader = (KindHeaderView) view.findViewById(R.id.kind_header_view);
    mTeamA = (TeamScoreRowView) view.findViewById(R.id.team_score_row_a);
    mTeamB = (TeamScoreRowView) view.findViewById(R.id.team_score_row_b);
    mVsRow = (VsRowView) view.findViewById(R.id.vs_row);
    mSubmitButton = (InputGameSubmitView) view.findViewById(R.id.input_game_submit_view);
    mAdapter = new PlayerSelectorSpinnerAdapter(getActivity(), mFragmentId);

    return view;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    mBus.unregister(this);
  }

  @Subscribe
  public void handlePlayerASelectedEvent(PlayerASelectedEvent event) {
    if(event.getTeam().equals("A")) {
      Log.e("MATT", "HAHAHAHHAHAHAHAHAHHAHAHAHHAHAHAHHAHAHA   " + mTeamA.getPlayerAName());
      this.teamAPlayerAName = mTeamA.getPlayerAName();
    } else {
      this.teamBPlayerAName = mTeamB.getPlayerAName();
    }
  }

  @Subscribe
  public void handlePlayerBSelectedEvent(PlayerBSelectedEvent event) {
    if(event.getTeam().equals("A")) {
      this.teamAPlayerBName = mTeamA.getPlayerBName();
    } else {
      this.teamBPlayerBName = mTeamB.getPlayerBName();
    }
  }

  @Subscribe
  public void handleScoreChangeClickEvent(ScoreChangeClickEvent event) {
    if(event.getTeam().equals("A")) {
      this.teamAScore = event.getScoreFromEvent();
    } else {
      this.teamBScore = event.getScoreFromEvent();
    }
  }

  @Subscribe
  public void handleSubmitGameEvent(SubmitGameClickEvent event) {
    int i = 9;
//    gatherEventInfo(event);

    // ready to go?
    if(isScoreSheetComplete()) {

      // create Game object
      Game game = new Game(teamAPlayerAName,
          teamAPlayerBName,
          teamAScore == null ? 0 : teamAScore.getInt(),
          teamBPlayerAName,
          teamBPlayerBName,
          teamBScore == null ? 0 : teamBScore.getInt());

      // submit game
      submitGame(game);

      // team A is a TEAM
      if(!teamAPlayerAName.isEmpty() && !teamAPlayerBName.isEmpty()){
        // alphabatize team
        if(teamAPlayerBName.compareTo(teamAPlayerAName) < 0) {
          String temp = teamAPlayerAName;
          teamAPlayerAName = teamAPlayerBName;
          teamAPlayerBName = temp;
        }
        updateATeam();
      }

      // team B is a TEAM
      if (!teamBPlayerAName.isEmpty() && !teamBPlayerBName.isEmpty()) {
        // alphabatize team
        if (teamBPlayerBName.compareTo(teamBPlayerAName) < 0) {
          String temp = teamBPlayerAName;
          teamBPlayerAName = teamBPlayerBName;
          teamBPlayerBName = temp;
        }
        updateBTeam();
      }

      updateAIndivs();
      updateBIndivs();

      getActivity().finish();
    }
  }

  public void gatherEventInfo(TurnInScoreSheetEvent event) {
    if(event.getTeam().equals("A")) {
      teamAPlayerAName = event.getPlayerANameFromEvent();
      teamAPlayerBName = event.getPlayerBNameFromEvent();
      teamAScore = event.getScore();
      if(teamAPlayerAName.equals("") && teamAPlayerBName.equals("")){
        return;
      }
    } else if (event.getTeam().equals("B")) {
      teamBPlayerAName = event.getPlayerANameFromEvent();
      teamBPlayerBName = event.getPlayerBNameFromEvent();
      teamBScore = event.getScore();
      if(teamBPlayerAName.equals("") && teamBPlayerBName.equals("")){
        return;
      }
    } else {
      return;
    }
  }

  public void updateAIndivs() {
    Log.e("MATT", "time to update A's indivs");
    // update A's indivs
    ParseQuery<IndivStat> indivAQuery = ParseQuery.getQuery("IndivStat");
    String[] aNames = {teamAPlayerAName, teamAPlayerBName};
    indivAQuery.whereContainedIn("name", Arrays.asList(aNames));
    Log.e("MATT", "query array: " + Arrays.asList(aNames).toString());
    indivAQuery.findInBackground(new FindCallback<IndivStat>() {
      @Override
      public void done(List<IndivStat> indivStats, ParseException e) {
        if (e == null) {
          for (IndivStat stat : indivStats) {
            stat.increment("wins", teamAScore == null ? 0 : teamAScore.getInt());
            stat.increment("losses", teamBScore == null ? 0 : teamBScore.getInt());
            stat.saveInBackground();
          }

        }
      }
    });
  }

  public void updateBIndivs() {
    // update B's indivs
    ParseQuery<IndivStat> indivBQuery = ParseQuery.getQuery("IndivStat");
    String[] bNames = {teamBPlayerAName, teamBPlayerBName};
    indivBQuery.whereContainedIn("name", Arrays.asList(bNames));
    indivBQuery.findInBackground(new FindCallback<IndivStat>() {
      @Override
      public void done(List<IndivStat> indivStats, ParseException e) {
        if (e == null) {
          for (IndivStat stat : indivStats) {
            stat.increment("wins", teamBScore == null ? 0 : teamBScore.getInt());
            stat.increment("losses", teamAScore == null ? 0 : teamAScore.getInt());
            stat.saveInBackground();
          }
        }
      }
    });
  }

  public void updateATeam() {
    // update team A
    ParseQuery<TeamStat> teamQuery = ParseQuery.getQuery("TeamStat");
    teamQuery.whereEqualTo("player1", teamAPlayerAName);
    teamQuery.whereEqualTo("player2", teamAPlayerBName);
    teamQuery.findInBackground(new FindCallback<TeamStat>() {
      @Override
      public void done(List<TeamStat> teamStats, ParseException e) {
        if (e == null) {
          if (teamStats.size() < 1) {
            Log.e("MATT", "A CREATION");

            // create new team
            TeamStat newTeam = new TeamStat();
            newTeam.put("player1", teamAPlayerAName);
            newTeam.put("player2", teamAPlayerBName);
            newTeam.put("wins", teamAScore == null ? 0 : teamAScore.getInt());
            newTeam.put("losses", teamBScore == null ? 0 : teamBScore.getInt());
            newTeam.saveInBackground();
          } else {
            teamStats.get(0).increment("wins", teamAScore == null ? 0 : teamAScore.getInt());
            teamStats.get(0).increment("losses", teamBScore == null ? 0 : teamBScore.getInt());
            teamStats.get(0).saveInBackground();
          }
        } else {
          Log.e("MATT", "err: " + e);
        }
      }
    });
  }

  public void updateBTeam() {
    Log.e("MATT", "Team B is a team");
    // update team B
    ParseQuery<TeamStat> teamBQuery = ParseQuery.getQuery("TeamStat");
    teamBQuery.whereEqualTo("player1", teamBPlayerAName);
    teamBQuery.whereEqualTo("player2", teamBPlayerBName);
    teamBQuery.findInBackground(new FindCallback<TeamStat>() {
      @Override
      public void done(List<TeamStat> teamStats, ParseException e) {
        if (e == null) {
          if (teamStats.size() == 0) {
            Log.e("MATT", "B CREATION");
            // create new team
            TeamStat newTeam = new TeamStat();
            newTeam.put("player1", teamBPlayerAName);
            newTeam.put("player2", teamBPlayerBName);
            newTeam.put("wins", teamBScore == null ? 0 : teamBScore.getInt());
            newTeam.put("losses", teamAScore == null ? 0 : teamAScore.getInt());
            newTeam.saveInBackground();
          } else {
            teamStats.get(0).increment("wins", teamBScore == null ? 0 : teamBScore.getInt());
            teamStats.get(0).increment("losses", teamAScore == null ? 0 : teamAScore.getInt());
            teamStats.get(0).saveInBackground();
          }
        } else {
          Log.e("MATT", "err: " + e);
        }
      }
    });
  }

  public void submitGame(Game game) {
    Game gameObj = new Game();
    gameObj.put("TeamAPlayerA", game.teamAPlayerA);
    gameObj.put("TeamAPlayerB", game.teamAPlayerB);
    gameObj.put("TeamAScore", game.teamAScore);
    gameObj.put("TeamBPlayerA", game.teamBPlayerA);
    gameObj.put("TeamBPlayerB", game.teamBPlayerB);
    gameObj.put("TeamBScore", game.teamBScore);
    gameObj.saveInBackground();
  }

  public boolean isScoreSheetComplete() {
    int points = 0;
    if((teamAPlayerAName != null && !teamAPlayerAName.equals("")) ||
        (teamAPlayerBName != null && !teamAPlayerBName.equals("")))
      points++;
    if((teamBPlayerAName != null && !teamBPlayerAName.equals("")) ||
        (teamBPlayerBName != null && !teamBPlayerBName.equals("")))
      points++;
    return points == 2;
  }

}
