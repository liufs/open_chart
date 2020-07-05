package com.example.myapplication.Repo;

import com.github.mikephil.charting.data.Entry;

public interface GateDataListener {
  void addGate(int id, Entry entry);
  void removeGate(int id);
}
