package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.Repo.DataFactory;
import com.example.myapplication.view.IntervalTestBinder;
import com.example.myapplication.view.LineChartBinder;
import com.example.myapplication.view.ViewBinder;

import java.util.ArrayList;

import com.example.myapplication.utils.Constant;

public class RefreshLineActivity extends Activity {
  private static final String TAG = "RefreshLineActivity";

  private ArrayList<ViewBinder> mViewBinderList = new ArrayList<>();
  private DataFactory mDataFactory = new DataFactory(Constant.INTERVAL);


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.refresh_line_content);
    View root = findViewById(R.id.root);
    initViewBinder(root);
  }

  private void initViewBinder(View root) {
    mViewBinderList.add(new LineChartBinder(mDataFactory));
    mViewBinderList.add(new IntervalTestBinder(mDataFactory));
    for (ViewBinder viewBinder : mViewBinderList) {
      viewBinder.doBindView(root);
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    for (ViewBinder viewBinder : mViewBinderList) {
      viewBinder.onAttach();
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    for (ViewBinder viewBinder : mViewBinderList) {
      viewBinder.onDetach();
    }
  }
}
