package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);



//    ArrayList<PointValue> values = new ArrayList<>();//折线上的点
//    values.add(new PointValue(0, 2));
//    values.add(new PointValue(1, 4));
//    values.add(new PointValue(2, 3));
//    values.add(new PointValue(3, 4));
//
//    Line line = new Line(values).setColor(Color.BLUE);//声明线并设置颜色
//    line.setCubic(false);//设置是平滑的还是直的
//    ArrayList<Line> lines = new ArrayList<>();
//    lines.add(line);
//
//    LineChartView mChartView = findViewById(R.id.chart);
//    mChartView.setInteractive(true);//设置图表是可以交互的（拖拽，缩放等效果的前提）
//    mChartView.setZoomType(ZoomType.HORIZONTAL_AND_VERTICAL);//设置缩放方向
//    LineChartData data = new LineChartData();
//    Axis axisX = new Axis();//x轴
//    Axis axisY = new Axis();//y轴
//    data.setAxisXBottom(axisX);
//    data.setAxisYLeft(axisY);
//    data.setLines(lines);
//    mChartView.setLineChartData(data);//给图表设置数据
  }
}
