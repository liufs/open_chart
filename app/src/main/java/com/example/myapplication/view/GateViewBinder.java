package com.example.myapplication.view;

import android.app.Activity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.Repo.DataFactory;
import com.example.myapplication.Repo.GateData;
import com.example.myapplication.Repo.GateDataListener;
import com.example.myapplication.utils.Constant;
import com.example.myapplication.widget.GateLineChartView;
import com.example.myapplication.widget.GateView;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.MPPointD;

import java.util.ArrayList;

public class GateViewBinder implements ViewBinder {

  private static final String TAG = "GateViewBinder";

  private DataFactory mDataFactory;
  private Activity mActivity;

  private GateLineChartView mLineChartView;
  private ArrayList<GateData> mDatas;

  public GateViewBinder(Activity activity, DataFactory dataFactory) {
    mActivity = activity;
    mDataFactory = dataFactory;
    mDatas = mDataFactory.getGateDatas();
    mDataFactory.setGateDataListener(new GateDataListener() {
      @Override
      public void addGate(int id, Entry entry) {
        for (GateData data : mDatas) {
          if (data.getId() == id) {
            data.getEntry().setX(entry.getX());
            data.getEntry().setY(entry.getY());
            mLineChartView.invalidate();
            return;
          }
        }
        GateData data = new GateData(id, entry, new GateView(mActivity, R.layout.mark_view_test));
        initGestureDetector(data.getGateView());
        mLineChartView.addGateView(data);
        mDatas.add(data);

      }

      @Override
      public void removeGate(int id) {
        for (int i = 0; i < mDatas.size(); i++) {
          if (mDatas.get(i).getId() == id) {
            mDatas.remove(i);
            return;
          }
        }
        Log.i(TAG, "removeGate not find gate id: " + id);
      }
    });
  }

  @Override
  public void doBindView(View rootView) {
    mLineChartView = rootView.findViewById(R.id.refresh_line);
  }

  @Override
  public void onAttach() {

  }

  @Override
  public void onDetach() {

  }

  private void initGestureDetector(GateView gateView) {
    GestureDetector gestureDetector = new GestureDetector(mActivity,
        new GestureDetector.SimpleOnGestureListener() {
      @Override
      public void onLongPress(MotionEvent e) {
        for (GateData data : mDatas) {
          if (data.getGateView() == gateView && gateView.isTouchPointInView(e.getX(), e.getY())) {
            if (data.getGateView().isIntercept()) {
              data.getGateView().setIntercept(false);
              Toast.makeText(mActivity, "关闭闸门编辑功能 id: " + data.getId(), Toast.LENGTH_SHORT).show();
            } else {
              for (GateData data1 : mDatas) {
                data1.getGateView().setIntercept(false);
              }
              data.getGateView().setIntercept(true);
              Toast.makeText(mActivity, "开启闸门编辑功能 id: " + data.getId(), Toast.LENGTH_SHORT).show();
            }
            return;
          }
        }

      }

      @Override
      public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        for (GateData data : mDatas) {
          if (data.getGateView() == gateView && gateView.isIntercept()) {
            if (gateView.isInScaleRange(e2.getX(), e2.getY())) {
              float w = gateView.getContentViewWidth() - distanceX;
              Log.i(TAG, "new width: " + w + " distanceX: " + distanceX);
              if (w <= Constant.GATE_MIN_WIDTH) {
                return true;
              }
              gateView.changeWidth((int) w);
              mLineChartView.invalidate();
              return true;
            }
            int drawX = (int) gateView.getDrawX();
            int drawY = (int) gateView.getDrawY();
            Log.i(TAG, "drawX: " + drawX + " drawY: " + drawY);
            MPPointD entry = mLineChartView.getValuesByTouchPoint(drawX - distanceX,
                drawY - distanceY, YAxis.AxisDependency.LEFT);
            Log.i(TAG, "new Entry: " + entry.x + " : " + entry.y);
            data.getEntry().setX((float) entry.x);
            data.getEntry().setY((float) entry.y);
            mLineChartView.invalidate();
            return super.onScroll(e1, e2, distanceX, distanceY);
          }
        }
        return super.onScroll(e1, e2, distanceX, distanceY);
      }
    });
    gateView.setGestureDetector(gestureDetector);
  }
}
