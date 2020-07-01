package com.example.myapplication.view;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.Repo.DataFactory;

public class IntervalTestBinder implements ViewBinder {

  private DataFactory mDataFactory;
  private EditText mSettingView;
  private Button mOKView;

  @Override
  public void doBindView(View rootView) {
    mSettingView = rootView.findViewById(R.id.interval_set);
    mOKView = rootView.findViewById(R.id.setting_ok);
    mOKView.setOnClickListener(v-> {
      mDataFactory.setInterval(Integer.valueOf(mSettingView.getText().toString()));
    });
  }

  public IntervalTestBinder(DataFactory dataFactory){
    mDataFactory =  dataFactory;
  }
}
