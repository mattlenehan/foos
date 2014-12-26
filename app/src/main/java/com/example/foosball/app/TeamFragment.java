package com.example.foosball.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foosball.app.ui.KindHeaderView;

import java.util.UUID;

public class TeamFragment extends Fragment {

//  private String mFragmentId;
//  private TeamStatsListAdapter mAdapter;
//
//  private ListView mListView;
//  private KindHeaderView mKindHeader;
//
//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//
//    mFragmentId = UUID.randomUUID().toString();
////    mAdapter = new TeamStatsListAdapter(getActivity(), mFragmentId);
//  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

    View rootView = inflater.inflate(R.layout.fragment_team, container, false);

    return rootView;
  }
}