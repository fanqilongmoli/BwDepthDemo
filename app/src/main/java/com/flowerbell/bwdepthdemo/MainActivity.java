package com.flowerbell.bwdepthdemo;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.Utils;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnChartGestureListener {

    private BwLineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineChart = ((BwLineChart) findViewById(R.id.lineChart));

        initChat();
    }

    private void initChat() {
        // 手势
        lineChart.setOnChartGestureListener(this);
        // 打开触摸
        lineChart.setTouchEnabled(true);
        // 缩放
        lineChart.setScaleYEnabled(false);

        lineChart.setScaleXEnabled(true);
        // 拖拽
        lineChart.setDragEnabled(true);

        lineChart.getDescription().setEnabled(false);


        // 例子
        Legend legend = lineChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);


        // 设置y
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

        // 关闭 右边y
        lineChart.getAxisRight().setEnabled(true);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getAxisRight().setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisRight().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String s;
                if (value > 10000) {
                    s = (int) (value / 10000) + "w";
                } else if (value > 1000) {
                    s = (int) (value / 1000) + "k";
                } else {
                    if (value == 0) {
                        return "";
                    }
                    s = (int) value + "";
                }
                return String.format(Locale.getDefault(), "%1$5s", s);
            }
        });
        leftAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String s;
                if (value > 10000) {
                    s = (int) (value / 10000) + "w";
                } else if (value > 1000) {
                    s = (int) (value / 1000) + "k";
                } else {
                    if (value == 0) {
                        return "";
                    }
                    s = (int) value + "";
                }
                return String.format(Locale.getDefault(), "%1$5s", s);
            }
        });

        // 设置x
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DefaultAxisValueFormatter(8));



        setData();

    }


    private void setData() {

        DepthUtil depthUtil = new DepthUtil();
        depthUtil.depthParse(TempData.Depth);


        List<Entry> entryList = depthUtil.getBuyEntryList();
        LineDataSet buySet = new LineDataSet(entryList, "买");
        // 设置 高亮的虚线

        buySet.setHighLightColor(Color.BLACK);
        buySet.setColor(Color.BLUE);
        buySet.setCircleColor(Color.BLACK);
        buySet.setLineWidth(1f);
        buySet.setDrawCircles(false);
        buySet.setDrawCircleHole(false);
        buySet.setDrawValues(false);
        buySet.setValueTextSize(9f);
        buySet.setDrawFilled(true);
        buySet.setFormLineWidth(1f);
        buySet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        buySet.setFormSize(15.f);
        buySet.setMode(LineDataSet.Mode.STEPPED);
        buySet.setFillColor(Color.YELLOW);
//        if (Utils.getSDKInt() >= 18) {
//            // fill drawable only supported on api level 18 and above
//            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//            buySet.setFillDrawable(drawable);
//        } else {
//            buySet.setFillColor(Color.BLACK);
//        }


        List<Entry> sellEntryList = depthUtil.getSellEntryList();
        LineDataSet sellSet = new LineDataSet(sellEntryList, "卖");
        sellSet.setHighLightColor(Color.BLACK);
        sellSet.setColor(Color.GREEN);
        sellSet.setCircleColor(Color.BLACK);
        sellSet.setLineWidth(1f);
        sellSet.setDrawCircles(false);
        sellSet.setDrawCircleHole(false);
        sellSet.setDrawValues(false);
        sellSet.setValueTextSize(9f);
        sellSet.setDrawFilled(true);
        sellSet.setFormLineWidth(1f);
        sellSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
        sellSet.setFormSize(15.f);
        sellSet.setMode(LineDataSet.Mode.STEPPED);
        sellSet.setFillColor(Color.YELLOW);
//        if (Utils.getSDKInt() >= 18) {
//            // fill drawable only supported on api level 18 and above
//            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
//            sellSet.setFillDrawable(drawable);
//        } else {
//            sellSet.setFillColor(Color.BLACK);
//        }

        // 设置market  x
        BwLineChartXMarkerView xMarkerView = new BwLineChartXMarkerView(this, null);
        xMarkerView.setChartView(lineChart);
        lineChart.setXMarker(xMarkerView);
        // 设置market  y
        BwLineChartYMarkerView yMarkerView = new BwLineChartYMarkerView(this,1);
        yMarkerView.setChartView(lineChart);

        lineChart.setMarker(yMarkerView);


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(buySet); // add the datasets
        dataSets.add(sellSet);

        // create a data object with the datasets
        LineData data = new LineData(dataSets);


        // set data
        lineChart.setData(data);
//        }
    }


    //--------------------手势----------------
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        // 手势 打开拖拽
        //lineChart.setDragEnabled(true);
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {

    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {

    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {

    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {

    }
    //--------------------手势----------------
}
