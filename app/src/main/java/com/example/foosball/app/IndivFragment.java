package com.example.foosball.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.ui.BlankSpaceView;
import com.example.foosball.app.ui.KindHeaderView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.squareup.otto.Bus;

import org.json.JSONArray;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class IndivFragment extends Fragment {

  private Activity mParentActivity;
  private String mFragmentId;
  private IndivStatsListAdapter mAdapter;

  private SwipeRefreshLayout swipeContainer;

  private ListView mListView;
  private KindHeaderView mKindHeader;

  @Inject
  Bus mBus;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    FoosApplication.get().inject(this);
    mBus.register(this);
    mFragmentId = UUID.randomUUID().toString();
    mAdapter = new IndivStatsListAdapter(getActivity(), mFragmentId);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_indiv, container, false);
    mKindHeader = (KindHeaderView) view.findViewById(R.id.kind_head);
    mListView = (ListView) view.findViewById(R.id.indiv_stat_list);

    fetchIndivStats();
    setupListView();

    swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
    // Setup refresh listener which triggers new data loading
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        // Your code to refresh the list here.
        // Make sure you call swipeContainer.setRefreshing(false)
        // once the network request has completed successfully.
        fetchIndivStats();
      }
    });
    // Configure the refreshing colors
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light);


    return view;
  }

  public void setupListView() {
    mListView.setAdapter(mAdapter);
  }

  public void fetchIndivStats() {
    IndivStat.findInBackground(new FindCallback<IndivStat>() {
      @Override
      public void done(List<IndivStat> indivStats, ParseException e) {
        if(e == null) {
          mAdapter.clear();
          mAdapter.sort(indivStats);
          mAdapter.addAll(indivStats);
          mAdapter.setPlaces(indivStats);
          swipeContainer.setRefreshing(false);
          mAdapter.notifyDataSetChanged();
        }
      }
    });
  }
}