package com.flowerbell.bwdepthdemo.depth;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.flowerbell.bwdepthdemo.widget.BwLineChart;
import com.flowerbell.bwdepthdemo.widget.BwLineChartXMarkerView;
import com.flowerbell.bwdepthdemo.widget.BwLineChartYMarkerView;
import com.flowerbell.bwdepthdemo.R;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by MIT on 2018/1/28.
 */

public class BwDepthView extends LinearLayout implements OnChartGestureListener {

    private Context mContext;
    private BwLineChart mChart;

    public BwDepthView(Context context) {
        this(context, null);
    }

    public BwDepthView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BwDepthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_depth, this);
        mChart = ((BwLineChart) findViewById(R.id.lineChart));


        // 手势
        mChart.setOnChartGestureListener(this);
        // 打开触摸
        mChart.setTouchEnabled(true);
        // 缩放
        mChart.setScaleYEnabled(false);

        mChart.setScaleXEnabled(false);
        // 拖拽
        mChart.setDragEnabled(true);

        mChart.getDescription().setEnabled(false);


        // 例子
        Legend legend = mChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);


        // 设置y
        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setAxisMinimum(0);
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

        // 关闭 右边y
        mChart.getAxisRight().setEnabled(true);
        mChart.getAxisRight().setDrawLabels(false);
        mChart.getAxisRight().setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        mChart.getAxisRight().setDrawGridLines(false);
        mChart.getAxisRight().setAxisMinimum(0);
        mChart.getAxisRight().setValueFormatter(new IAxisValueFormatter() {
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
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new DefaultAxisValueFormatter(8));
    }

    public void setData(String json){
        DepthUtil depthUtil = new DepthUtil();
        depthUtil.depthParse(json);
        List<Entry> entryList = depthUtil.getBuyEntryList();
        LineDataSet buySet = new LineDataSet(entryList, "买");
        // 设置 高亮的虚线

        buySet.setHighLightColor(Color.BLACK);
        buySet.setColor(Color.GREEN);
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
        buySet.setFillColor(Color.GREEN);



        List<Entry> sellEntryList = depthUtil.getSellEntryList();
        LineDataSet sellSet = new LineDataSet(sellEntryList, "卖");
        sellSet.setHighLightColor(Color.BLACK);
        sellSet.setColor(Color.RED);
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
        sellSet.setFillColor(Color.RED);

        // 设置market  x
        BwLineChartXMarkerView xMarkerView = new BwLineChartXMarkerView(mContext, null);
        xMarkerView.setChartView(mChart);
        mChart.setXMarker(xMarkerView);
        // 设置market  y
        BwLineChartYMarkerView yMarkerView = new BwLineChartYMarkerView(mContext,1);
        yMarkerView.setChartView(mChart);

        mChart.setMarker(yMarkerView);


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(buySet); // add the datasets
        dataSets.add(sellSet);

        // create a data object with the datasets
        LineData data = new LineData(dataSets);

        mChart.setData(data);

    }


    //========手势========//
    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

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
    //========手势========//
}
