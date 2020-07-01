package com.example.myapplication.Repo;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class DataFactory {

  private DecimalFormat mDecimalFormat = new DecimalFormat("#.00");   // 格式化浮点数位两位小数
  private Random mRandom = new Random(); // 随机产生点

  private PublishSubject<List<Float>> mDataChangeSubject = PublishSubject.create();
  private int mInterval;
  private boolean mIsLoop = true;

  public DataFactory(int interval) {
    mInterval = interval;
    startCreateData();
  }

  public Observable<List<Float>> subscribeDataChange() {
    return mDataChangeSubject;
  }

  private void startCreateData() {
    new Thread(() -> {
      while (mIsLoop) {
        List<Float> data = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
          data.add(getRandom(30f));
        }
        mDataChangeSubject.onNext(data);
        try {
          Thread.sleep(mInterval);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }).start();
  }

  /**
   * 功能：产生随机数（小数点两位）
   */
  public Float getRandom(Float seed) {
    return Float.valueOf(mDecimalFormat.format(mRandom.nextFloat() * seed));
  }

  public void setIsLoop(boolean isLoop) {
    this.mIsLoop = isLoop;
  }

  public void setInterval(int interval){
    mInterval = interval;
  }


}
