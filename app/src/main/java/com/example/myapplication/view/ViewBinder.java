package com.example.myapplication.view;

import android.view.View;

/**
 * view 处理
 */
public interface ViewBinder {
  void doBindView(View rootView);

  default void onAttach() {}

  default void onDetach() {}
}
