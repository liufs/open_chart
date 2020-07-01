package com.example.myapplication.view;

import android.graphics.Color;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.Repo.DataFactory;
import com.example.myapplication.utils.Constant;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class LineChartBinder implements ViewBinder {

  // Chart需要的点数据链表
  private List<Entry> mEntries1 = new ArrayList<>();
  // LineDataSet:点集合,即一条线
  private LineDataSet mLineDataSet1 = new LineDataSet(mEntries1, "折线1");
  private LineChart mLineChart;
  private LineData mLineData; // 线集合，所有折现以数组的形式存到此集合中
  private XAxis mXAxis; //X轴
  private YAxis mLeftYAxis; //左侧Y轴
  private YAxis mRightYAxis; //右侧Y轴

  private DataFactory mDataFactory;

  public LineChartBinder(DataFactory dataFactory) {
    mDataFactory = dataFactory;
  }

  @Override
  public void doBindView(View rootView) {
    mLineChart = rootView.findViewById(R.id.refresh_line);
    mXAxis = mLineChart.getXAxis(); // 得到x轴
    mLeftYAxis = mLineChart.getAxisLeft(); // 得到侧Y轴
    mRightYAxis = mLineChart.getAxisRight(); // 得到右侧Y轴
    mLineData = new LineData();
    mLineChart.setData(mLineData);

    // 设置图标基本属性
    setChartBasicAttr(mLineChart);

    // 设置XY轴
    setXYAxis(mLineChart, mXAxis, mLeftYAxis, mRightYAxis);
  }

  @Override
  public void onAttach() {
    refreshData();
  }

  @Override
  public void onDetach() {
    mDataFactory.setIsLoop(false);
  }

  /**
   * 功能：设置图标的基本属性
   */
  void setChartBasicAttr(LineChart lineChart) {
    /***图表设置***/
    lineChart.setDrawGridBackground(true); //是否展示网格线
    lineChart.setDrawBorders(true); //是否显示边界
    lineChart.setDragEnabled(true); //是否可以拖动
    lineChart.setScaleEnabled(true); // 是否可以缩放
    lineChart.setTouchEnabled(true); //是否有触摸事件
    lineChart.moveViewToX(-250); // 向左移动250刻度
  }


  /**
   * 功能：设置XY轴
   */
  void setXYAxis(LineChart lineChart, XAxis xAxis, YAxis leftYAxis, YAxis rightYAxis) {
    /***XY轴的设置***/
    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //X轴设置显示位置在底部
    xAxis.setAxisMinimum(Constant.MINIMUM_X); // 设置X轴的最小值
    xAxis.setAxisMaximum(Constant.MAXIMUM_X); // 设置X轴的最大值
    xAxis.setLabelCount(Constant.LABEL_COUNT_X, true); // 设置X轴的刻度数量，第二个参数表示是否平均分配
    //    xAxis.setGranularity(1f); // 设置X轴坐标之间的最小间隔
    lineChart.setVisibleXRangeMaximum(Constant.MAXIMUM_RANGE_X);// 当前统计图表中最多在x轴坐标线上显示的总量
    //保证Y轴从0开始，不然会上移一点
    leftYAxis.setAxisMinimum(0f);
    rightYAxis.setAxisMinimum(0f);
    leftYAxis.setAxisMaximum(100f);
    rightYAxis.setAxisMaximum(100f);
    leftYAxis.setGranularity(1f);
    rightYAxis.setGranularity(1f);
    leftYAxis.setLabelCount(20);
    lineChart.setVisibleYRangeMaximum(30, YAxis.AxisDependency.LEFT);// 当前统计图表中最多在Y轴坐标线上显示的总量
    lineChart.setVisibleYRangeMaximum(30, YAxis.AxisDependency.RIGHT);// 当前统计图表中最多在Y轴坐标线上显示的总量
    leftYAxis.setEnabled(false);
  }

  /**
   * 功能：动态创建一条曲线
   */
  private void createLine(List<Float> dataList, List<Entry> entries, LineDataSet lineDataSet,
                          int color, LineData lineData, LineChart lineChart) {
    for (int i = 0; i < dataList.size(); i++) {
      /**
       * 在此可查看 Entry构造方法，可发现 可传入数值 Entry(float x, float y)
       * 也可传入Drawable， Entry(float x, float y, Drawable icon) 可在XY轴交点 设置Drawable图像展示
       */
      Entry entry = new Entry(i - 250, dataList.get(i));// Entry(x,y)
      entries.add(entry);
    }

    // 初始化线条
    initLineDataSet(lineDataSet, color, LineDataSet.Mode.CUBIC_BEZIER);

    if (lineData == null) {
      lineData = new LineData();
      lineData.addDataSet(lineDataSet);
      lineChart.setData(lineData);
    } else {
      lineChart.getLineData().addDataSet(lineDataSet);
    }

    lineChart.invalidate();
  }

  /**
   * 曲线初始化设置,一个LineDataSet 代表一条曲线
   *
   * @param lineDataSet 线条
   * @param color       线条颜色
   * @param mode
   */
  private void initLineDataSet(LineDataSet lineDataSet, int color, LineDataSet.Mode mode) {
    lineDataSet.setColor(color); // 设置曲线颜色
    lineDataSet.setCircleColor(color);  // 设置数据点圆形的颜色
    lineDataSet.setDrawCircleHole(false);// 设置曲线值的圆点是否是空心
    lineDataSet.setLineWidth(1f); // 设置折线宽度
    lineDataSet.setCircleRadius(1f); // 设置折现点圆点半径
    lineDataSet.setValueTextSize(10f);
    lineDataSet.setDrawCircles(false);
    lineDataSet.setDrawValues(true);

    lineDataSet.setDrawFilled(true); //设置折线图填充
    lineDataSet.setFormLineWidth(1f);
    lineDataSet.setFormSize(15.f);
    if (mode == null) {
      //设置曲线展示为圆滑曲线（如果不设置则默认折线）
      lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
    } else {
      lineDataSet.setMode(mode);
    }
  }

  private void refreshData() {
    Disposable d =
        mDataFactory.subscribeDataChange().observeOn(AndroidSchedulers.mainThread()).subscribe(list -> {
      mEntries1.clear();
      mLineDataSet1.clear();
      mLineData.clearValues();
      createLine(list, mEntries1, mLineDataSet1, Color.RED, mLineData, mLineChart);
    }, error -> {

    });
  }

}
