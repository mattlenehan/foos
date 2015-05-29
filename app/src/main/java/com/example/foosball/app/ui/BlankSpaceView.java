package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.foosball.app.R;

public class BlankSpaceView extends FrameLayout {

  public BlankSpaceView(Context context) {
    this(context, null);
  }

  public BlankSpaceView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BlankSpaceView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    LayoutInflater inflater = LayoutInflater.from(context);
    inflater.inflate(R.layout.blank_space, this);
  }
}
