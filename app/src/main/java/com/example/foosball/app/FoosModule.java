package com.example.foosball.app;

import android.util.Log;

import javax.inject.Singleton;

import com.example.foosball.app.event.PlayerASelectedEvent;
import com.example.foosball.app.event.PlayerBSelectedEvent;
import com.example.foosball.app.event.ScoreChangeClickEvent;
import com.example.foosball.app.event.SubmitGameClickEvent;
import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.model.TeamStat;
import com.example.foosball.app.model.Game;
import com.example.foosball.app.ui.BlankSpaceView;
import com.example.foosball.app.ui.IndividualStatRowView;
import com.example.foosball.app.ui.InputGameSubmitView;
import com.example.foosball.app.ui.IntegerView;
import com.example.foosball.app.ui.KindHeaderView;
import com.example.foosball.app.ui.SegmentedGroupView;
import com.example.foosball.app.ui.TeamScoreRowView;
import com.example.foosball.app.ui.TeamStatRowView;
import com.example.foosball.app.ui.VsRowView;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;
import foosball.tabswipe.adapter.TabsPagerAdapter;

@Module (
  injects = {
      PlayerASelectedEvent.class,
      PlayerBSelectedEvent.class,
      ScoreChangeClickEvent.class,
      SubmitGameClickEvent.class,
      IndivStat.class,
      TeamStat.class,
      Game.class,
      BlankSpaceView.class,
      IndividualStatRowView.class,
      InputGameSubmitView.class,
      IntegerView.class,
      KindHeaderView.class,
      SegmentedGroupView.class,
      TeamStatRowView.class,
      TeamScoreRowView.class,
      VsRowView.class,
      IndivFragment.class,
      IndivStatsListAdapter.class,
      TeamStatsListAdapter.class,
      InputGameActivity.class,
      InputGameFragment.class,
      MainActivity.class,
      PlayerSelectorSpinnerAdapter.class,
      TeamFragment.class,
      TabsPagerAdapter.class,
      FoosApplication.class
  },
    library = false
)

public class FoosModule {

  private final FoosApplication application;

  public FoosModule(FoosApplication application) {
    this.application = application;
  }

  @Provides
  @Singleton
  public Bus providerBus() {
    return new Bus();
  }
}
