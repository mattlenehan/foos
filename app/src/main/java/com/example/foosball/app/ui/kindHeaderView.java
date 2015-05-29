package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.foosball.app.R;

public class KindHeaderView extends FrameLayout {

  public KindHeaderView(Context context) {
    this(context, null);
  }

  public KindHeaderView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public KindHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

//    this.setLayoutParams(getLayoutParams());

    LayoutInflater inflater = LayoutInflater.from(context);
    inflater.inflate(R.layout.kind_header, this);
  }

}