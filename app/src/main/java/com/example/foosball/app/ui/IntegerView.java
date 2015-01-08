package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class IntegerView extends TextView {

  public IntegerView(Context context) {
    this(context, null);
  }

  public IntegerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public IntegerView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  public void setInt(int value) {
    setText(String.valueOf(value));
  }
  public int getInt() {
    return Integer.valueOf(getText().toString());
  }
}
