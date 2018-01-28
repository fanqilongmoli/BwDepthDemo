package com.flowerbell.bwdepthdemo.kline;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.flowerbell.bwdepthdemo.R;
import com.flowerbell.bwdepthdemo.widget.CoupleChartGestureListener;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.Locale;

/**
 * Created by MIT on 2018/1/28.
 */

public class BwKlineView extends LinearLayout {

    private Context mContext;
    private CombinedChart mChartPrice;
    private CombinedChart mChartVolume;

    public BwKlineView(Context context) {
        this(context,null);
    }

    public BwKlineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BwKlineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_kline,this);

        mChartPrice = ((CombinedChart) findViewById(R.id.lineChart));
        mChartVolume = ((CombinedChart) findViewById(R.id.barChat));

        // 分时  k线段
        initChartPrice();
        // 数量 等等
        initChartVolume();
        // 设置监听
        initChartListener();
    }


    private void initChartPrice() {
        mChartPrice.setScaleEnabled(true);
        mChartPrice.setDrawBorders(false);
        mChartPrice.setDragEnabled(true);
        mChartPrice.setScaleYEnabled(false);
        mChartPrice.getDescription().setEnabled(false);
        mChartPrice.setAutoScaleMinMaxEnabled(true);

        // 不显示 例子
        Legend lineChartLegend = mChartPrice.getLegend();
        lineChartLegend.setEnabled(false);

        // x 不显示
        mChartPrice.getXAxis().setDrawLabels(false);
        mChartPrice.getXAxis().setDrawAxisLine(false);
        mChartPrice.getXAxis().setDrawGridLines(false);
        mChartPrice.getXAxis().setAxisMinimum(-0.5f);

        // left Y
        mChartPrice.getAxisLeft().setLabelCount(5, true);
        mChartPrice.getAxisLeft().setDrawLabels(true);
        mChartPrice.getAxisLeft().setDrawGridLines(false);
        mChartPrice.getAxisLeft().setDrawAxisLine(false);
        mChartPrice.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);

        // right Y
        mChartPrice.getAxisRight().setLabelCount(5, true);
        mChartPrice.getAxisRight().setDrawLabels(false);
        mChartPrice.getAxisRight().setDrawGridLines(false);
        mChartPrice.getAxisRight().setDrawAxisLine(false);
        mChartPrice.getAxisRight().setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);


    }

    private void initChartVolume() {

        mChartVolume.setScaleEnabled(true);
        mChartVolume.setDrawBorders(false);
        mChartVolume.setBorderWidth(1);
        mChartVolume.setDragEnabled(true);
        mChartVolume.setScaleYEnabled(false);
        mChartVolume.getDescription().setEnabled(false);
        mChartVolume.setAutoScaleMinMaxEnabled(true);
        mChartVolume.setDragDecelerationEnabled(false);
        mChartVolume.setHighlightPerDragEnabled(false);
        Legend lineChartLegend = mChartVolume.getLegend();
        lineChartLegend.setEnabled(false);


        mChartVolume.getXAxis().setDrawLabels(true);
        mChartVolume.getXAxis().setDrawAxisLine(false);
        mChartVolume.getXAxis().setDrawGridLines(false);
        mChartVolume.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        mChartVolume.getXAxis().setLabelCount(5, true);
        mChartVolume.getXAxis().setAvoidFirstLastClipping(true);
        mChartVolume.getXAxis().setAxisMinimum(-0.5f);

        //xAxisVolume.setValueFormatter(new KLineXValueFormatter(mData));


        mChartVolume.getAxisLeft().setDrawLabels(true);
        mChartVolume.getAxisLeft().setDrawGridLines(false);
        mChartVolume.getAxisLeft().setLabelCount(3, true);
        mChartVolume.getAxisLeft().setDrawAxisLine(false);
        mChartVolume.getAxisLeft().setAxisMinimum(0);
        mChartVolume.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        mChartVolume.getAxisLeft().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String s;
                if (value > 10000) {
                    s = (int) (value / 10000) + "w";
                } else if (value > 1000) {
                    s = (int) (value / 1000) + "k";
                } else {
                    s = (int) value + "";
                }
                return String.format(Locale.getDefault(), "%1$5s", s);
            }
        });


        //右边y
        mChartVolume.getAxisRight().setDrawLabels(false);
        mChartVolume.getAxisRight().setDrawGridLines(false);
        mChartVolume.getAxisRight().setDrawAxisLine(false);
    }


    private void initChartListener() {
        // 手势 同步
        mChartPrice.setOnChartGestureListener(new CoupleChartGestureListener(mChartPrice,mChartVolume));
        mChartVolume.setOnChartGestureListener(new CoupleChartGestureListener(mChartVolume,mChartPrice));

        // TODO: 2018/1/28 取值显示
    }








}
