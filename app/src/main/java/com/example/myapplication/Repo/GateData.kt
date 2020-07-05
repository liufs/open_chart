package com.example.myapplication.Repo

import com.example.myapplication.widget.GateView
import com.github.mikephil.charting.data.Entry

/*
* 闸门数据
* */
data class GateData(val id: Int, val entry: Entry, val gateView: GateView)