package com.example.foosball.app;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foosball.app.ui.KindHeaderView;
import com.example.foosball.app.ui.TeamScoreRowView;
import com.example.foosball.app.ui.VsRowView;

import java.util.UUID;

public class InputGameFragment extends Fragment {

  private Activity mParentActivity;
  private String mFragmentId;
  private PlayerSelectorSpinnerAdapter mAdapter;

  private ListView mListView;
  private KindHeaderView mKindHeader;
  private TeamScoreRowView mTeamA;
  private TeamScoreRowView mTeamB;
  private VsRowView mVsRow;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mFragmentId = UUID.randomUUID().toString();
    mAdapter = new PlayerSelectorSpinnerAdapter(getActivity(), mFragmentId);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.input_game_fragment, container, false);
    mListView = (ListView) view.findViewById(R.id.input_game_list_view);
    return view;
  }

}
