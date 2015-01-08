package com.example.foosball.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foosball.app.model.IndivStat;
import com.example.foosball.app.ui.KindHeaderView;
import com.parse.FindCallback;
import com.parse.ParseException;

import java.util.List;
import java.util.UUID;

public class IndivFragment extends Fragment {

  private Activity mParentActivity;
  private String mFragmentId;
  private IndivStatsListAdapter mAdapter;

  private ListView mListView;
  private KindHeaderView mKindHeader;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mFragmentId = UUID.randomUUID().toString();
    mAdapter = new IndivStatsListAdapter(getActivity(), mFragmentId);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_indiv, container, false);
    mKindHeader = new KindHeaderView(getActivity());
    mListView = (ListView) view.findViewById(R.id.indiv_stat_list);

    fetchIndivStats();
    setupListView();

    return view;
  }

  public void setupListView() {
    mListView.addHeaderView(mKindHeader);
    mListView.setAdapter(mAdapter);
  }

  public void fetchIndivStats() {
    IndivStat.findInBackground(new FindCallback<IndivStat>() {
      @Override
      public void done(List<IndivStat> indivStats, ParseException e) {
        if(e == null) {
          mAdapter.sort(indivStats);
          mAdapter.addAll(indivStats);
        }
      }
    });
  }
}