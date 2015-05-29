package com.example.foosball.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.foosball.app.FoosApplication;
import com.example.foosball.app.R;
import com.example.foosball.app.event.SubmitGameClickEvent;
import com.squareup.otto.Bus;

import javax.inject.Inject;

import at.markushi.ui.CircleButton;

/**
 * Created by mattlenehan on 1/13/15.
 */
public class InputGameSubmitView extends FrameLayout {

  // subviews
  private CircleButton mSubmitButton;

  @Inject
  Bus mBus;

  private Context mContext;

  public InputGameSubmitView(Context context) {
    this(context, null);
  }

  public InputGameSubmitView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public InputGameSubmitView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    FoosApplication.get().inject(this);
    mBus.register(this);
    mContext = context;
    LayoutInflater inflater = LayoutInflater.from(context);
    View v = inflater.inflate(R.layout.input_game_submit, this);
    mSubmitButton = (CircleButton) v.findViewById(R.id.submit_game_button);
    bindData();
  }

  public void bindData() {
    mSubmitButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.e("MATT","submit clicked");
        mBus.post(new SubmitGameClickEvent());
      }
    });
  }
}
