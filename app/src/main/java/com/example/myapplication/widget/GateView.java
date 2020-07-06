package com.example.myapplication.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.github.mikephil.charting.data.Entry;

import static com.example.myapplication.utils.Constant.SCALE_RANGE;

/**
 * 在图表中选出一块区域，获取XY轴的值
 */
public class GateView extends FrameLayout implements IGate {

  private boolean mIntercept = false;
  private Entry mData;
  private GestureDetector mGestureDetector;
  private float mDrawX, mDrawY;
  private View mContentView;

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

    mContentView = LayoutInflater.from(getContext()).inflate(layoutResource, this);
    mContentView.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    mContentView.layout(0, 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
  }

  public int getContentViewWidth() {
    return mContentView.getWidth();
  }

  public void changeWidth(int width) {
    mContentView.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
        MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
    mContentView.layout(0, 0, mContentView.getMeasuredWidth(), mContentView.getMeasuredHeight());
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

  public boolean isInScaleRange(float x, float y) {
    float top = mDrawY;
    float right = mDrawX + getMeasuredWidth();
    float bottom = mDrawY + getMeasuredHeight();
    if (y >= top && y <= bottom && x >= right - SCALE_RANGE && x <= right + SCALE_RANGE) {
      return true;
    }
    return false;
  }
}
