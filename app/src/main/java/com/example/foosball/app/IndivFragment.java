package com.example.foosball.app;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IndivFragment extends ListFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_indiv, container, false);

        String[] names = new String[]{"Matt", "Craig", "Mahesh", "Malay"};

        return rootView;
    }
}