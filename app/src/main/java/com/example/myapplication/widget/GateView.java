package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.github.mikephil.charting.data.Entry;

/**
 * 在图表中选出一块区域，获取XY轴的值
 */
public class GateView extends FrameLayout implements IGate {

  private boolean mIntercept = false;
  private Entry mData;
  private GestureDetector mGestureDetector;
  private float mDrawX, mDrawY;

  /**
   * Constructor. Sets up the MarkerView with a custom layout resource.
   *
   * @param context
   * @param layoutResource the layout resource to use for the MarkerView
   */
  public GateView(Context context, int layoutResource) {
    super(context);
    setupLayoutResource(layoutResource);
  }

  public float getDrawX() {
    return mDrawX;
  }

  public float getDrawY() {
    return mDrawY;
  }

  public boolean isIntercept() {
    return mIntercept;
  }

  public void setIntercept(boolean intercept) {
    this.mIntercept = intercept;
  }

  public Entry getData() {
    return mData;
  }

  public void setData(Entry data) {
    this.mData = data;
  }

  public void setGestureDetector(GestureDetector gestureDetector) {
    mGestureDetector = gestureDetector;
  }

  private void setupLayoutResource(int layoutResource) {

    View inflated = LayoutInflater.from(getContext()).inflate(layoutResource, this);

    inflated.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
    inflated.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

    // measure(getWidth(), getHeight());
    inflated.layout(0, 0, inflated.getMeasuredWidth(), inflated.getMeasuredHeight());
  }

  @Override
  public void draw(Canvas canvas, float posX, float posY) {
    int saveId = canvas.save();
    // translate to the correct position and draw
    mDrawX = posX;
    mDrawY = posY;
    canvas.translate(posX, posY);
    draw(canvas);
    canvas.restoreToCount(saveId);
  }

  @Override
  public boolean onTouchGate(MotionEvent event) {
    if (mIntercept) {
      mGestureDetector.onTouchEvent(event);
      return true;
    }
    if (mGestureDetector != null && isTouchPointInView((int) event.getX(), (int) event.getY())) {
      return mGestureDetector.onTouchEvent(event);
    }
    return false;
  }

  public boolean isTouchPointInView(float xAxis, float yAxis) {
    int[] location = new int[2];
    getLocationInWindow(location);
    float left = mDrawX;
    float top = mDrawY;
    Log.i("liufs", "left: " + left + " top: " + top);
    float right = left + getMeasuredWidth();
    float bottom = top + getMeasuredHeight();
    if (yAxis >= top && yAxis <= bottom && xAxis >= left && xAxis <= right) {
      return true;
    }
    return false;
  }
}
