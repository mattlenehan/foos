package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foosball.app.R;

/**
 * Created by mattlenehan on 11/22/14.
 */
public class TeamStatRowView extends FrameLayout {

    //sub views
    private ImageView mIconA;
    private ImageView mIconB;
    private TextView mWins;
    private TextView mLosses;
    private TextView mRate;

    public TeamStatRowView(Context context) {
        this(context, null);
    }

    public TeamStatRowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TeamStatRowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.team_stat_row, this);
        mIcon = (ImageView) v.findViewById(R.id.)
    }
}
