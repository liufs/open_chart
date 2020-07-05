package com.example.myapplication.widget;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface IGate {
  void draw(Canvas canvas, float posX, float posY);
  boolean onTouchGate(MotionEvent event);
}
