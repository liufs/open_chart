package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.example.myapplication.Repo.GateData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MPPointD;

import java.util.ArrayList;

public class GateLineChartView extends LineChart {

  ArrayList<GateData> mGatePoints = new ArrayList<>();

  public GateLineChartView(Context context) {
    super(context);
  }

  public GateLineChartView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public GateLineChartView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    for (GateData data : mGatePoints) {
      Entry e = data.getEntry();
      MPPointD mpPointD = getTransformer(YAxis.AxisDependency.LEFT).getPixelForValues(e.getX(),
          e.getY() * mAnimator.getPhaseY());
      data.getGateView().draw(canvas, (float) mpPointD.x, (float) mpPointD.y);
    }
  }

  public void addGateView(GateData data) {
    mGatePoints.add(data);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    for (GateData data : mGatePoints) {
      if (data.getGateView().onTouchGate(event)) {
        Log.i("touch", "gate touch");
        return true;
      }
    }
    Log.i("touch", "chart touch");
    return super.onTouchEvent(event);
  }
}
