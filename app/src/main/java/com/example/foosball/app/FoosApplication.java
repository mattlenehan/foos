package com.example.foosball.app;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import dagger.ObjectGraph;

public class FoosApplication extends MultiDexApplication {

  private static ObjectGraph mApplicationGraph;
  private static FoosApplication mApp;

  public static FoosApplication get(){
    return mApp;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mApp = this;
    mApplicationGraph = ObjectGraph.create(getModules().toArray());

    // inject dependencies
    FoosApplication.get().inject(get());
  }

  public static ObjectGraph getObjectGraph() {
    return mApplicationGraph;
  }

  public void inject(Object object) {
    getObjectGraph().inject(object);
  }

  protected List<Object> getModules() {
    return Arrays.<Object>asList(
        new FoosModule(this)
    );
  }
}
