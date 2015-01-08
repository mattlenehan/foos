package com.example.foosball.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.foosball.app.ui.KindHeaderView;
import com.example.foosball.app.ui.TeamScoreRowView;
import com.example.foosball.app.ui.VsRowView;

import java.util.List;
import java.util.UUID;

public class InputGameFragment extends Fragment {

  private Activity mParentActivity;
  private String mFragmentId;
  private PlayerSelectorSpinnerAdapter mAdapter;

  private LinearLayout mView;
  private KindHeaderView mKindHeader;
  private TeamScoreRowView mTeamA;
  private TeamScoreRowView mTeamB;
  private VsRowView mVsRow;

  public List<String> mOptions;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mFragmentId = UUID.randomUUID().toString();
    mKindHeader = new KindHeaderView(getActivity());
    mTeamA = new TeamScoreRowView(getActivity());
    mTeamB = new TeamScoreRowView(getActivity());
    mVsRow = new VsRowView(getActivity());
    mAdapter = new PlayerSelectorSpinnerAdapter(getActivity(), mFragmentId);

  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.input_game_fragment, container, false);
    return view;
  }
}
