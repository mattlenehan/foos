package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foosball.app.R;

/**
 * Created by mattlenehan on 11/22/14.
 */
public class IndividualStatRowView {

    //sub views
    private ImageView mIcon;
    private TextView mFirstName;
    private TextView mLastName;
    private TextView mWins;
    private TextView mLosses;
    private TextView mRate;

    public IndividualStatRowView(Context context) {
        this(context, null);
    }

    public IndividualStatRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndividualStatRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.individual_stat_row, this);
        mIcon = (ImageView) v.findViewById(R.id.indiv_player_icon);
        mFirstName = (TextView) v.findViewById(R.id.indiv_first_name);
    }
}
