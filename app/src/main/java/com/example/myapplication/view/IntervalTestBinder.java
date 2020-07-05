package com.example.myapplication.view;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.Repo.DataFactory;
import com.github.mikephil.charting.data.Entry;

public class IntervalTestBinder implements ViewBinder {

  private DataFactory mDataFactory;
  private EditText mSettingView;
  private Button mOKView;

  @Override
  public void doBindView(View rootView) {
    mSettingView = rootView.findViewById(R.id.interval_set);
    mOKView = rootView.findViewById(R.id.setting_ok);
    mOKView.setOnClickListener(v-> {
      if(TextUtils.isEmpty(mSettingView.getText().toString())){
//        mDataFactory.setIsLoop(false);
        Entry entry = new Entry();
        entry.setX(100);
        entry.setY(20);
        mDataFactory.addGateData(1,entry);
        entry = new Entry();
        entry.setX(-100);
        entry.setY(20);
        mDataFactory.addGateData(2,entry);
        return;
      }
      mDataFactory.setInterval(Integer.valueOf(mSettingView.getText().toString()));

    });
  }

  public IntervalTestBinder(DataFactory dataFactory){
    mDataFactory =  dataFactory;
  }
}
