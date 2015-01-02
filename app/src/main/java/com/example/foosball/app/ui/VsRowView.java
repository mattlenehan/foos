package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.foosball.app.R;

public class VsRowView extends FrameLayout {

  public VsRowView(Context context) {
    this(context, null);
  }

  public VsRowView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public VsRowView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);

    LayoutInflater inflater = LayoutInflater.from(context);
    inflater.inflate(R.layout.vs_row_view, this);
  }
}
