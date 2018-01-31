package com.flowerbell.bwdepthdemo.kline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
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
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by MIT on 2018/1/28.
 */

public class BwKlineView extends LinearLayout {

    private Context mContext;
    private CombinedChart mChartPrice;
    private CombinedChart mChartVolume;
    private Lock lock = new ReentrantLock();


    public int MAX_COUNT_LINE = 100;
    public int MIN_COUNT_LINE = 50;
    public int MAX_COUNT_K = 200;
    public int MIN_COUNT_K = 30;

    /**
     * average line
     */
    public static final int AVE_LINE = 1;
    public static final int MA5 = 5;
    public static final int MA10 = 10;
    public static final int MA20 = 20;
    public static final int MA30 = 30;
    public static final int NORMAL_LINE = 0;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mChartPrice.setAutoScaleMinMaxEnabled(true);
            mChartVolume.setAutoScaleMinMaxEnabled(true);


            mChartPrice.getXAxis().setAxisMinimum(0);
            mChartPrice.getXAxis().setAxisMaximum(mData.size());
            mChartVolume.getXAxis().setAxisMinimum(0);
            mChartVolume.getXAxis().setAxisMaximum(mData.size());


            mChartPrice.notifyDataSetChanged();
            mChartVolume.notifyDataSetChanged();

            mChartPrice.invalidate();
            mChartVolume.invalidate();
//            handler.sendEmptyMessageDelayed(0, 1000);
            return true;
        }
    });

    private List<KlineEntity> mData = new ArrayList<>();

    public BwKlineView(Context context) {
        this(context, null);
    }

    public BwKlineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BwKlineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.view_kline, this);

        mChartPrice = ((CombinedChart) findViewById(R.id.lineChart));
        mChartVolume = ((CombinedChart) findViewById(R.id.barChat));

        // 分时  k线段
        initChartPrice();
        // 数量 等等
        initChartVolume();

        // 设置监听
        initChartListener();

        handler.sendEmptyMessageDelayed(0, 1000);
    }


    private void initChartPrice() {
        mChartPrice.setScaleEnabled(true);
        mChartPrice.setDrawBorders(false);
        mChartPrice.setDragEnabled(true);
        mChartPrice.setScaleYEnabled(false);
        mChartPrice.getDescription().setEnabled(false);
        mChartPrice.setAutoScaleMinMaxEnabled(true);
        mChartPrice.setMinOffset(3f);

        // 不显示 例子
        Legend lineChartLegend = mChartPrice.getLegend();
        lineChartLegend.setEnabled(false);

        // x 不显示
        mChartPrice.getXAxis().setDrawLabels(false);
        mChartPrice.getXAxis().setDrawAxisLine(false);
        mChartPrice.getXAxis().setDrawGridLines(false);

        // left Y
        mChartPrice.getAxisLeft().setLabelCount(5, true);
        mChartPrice.getAxisLeft().setDrawLabels(true);
        mChartPrice.getAxisLeft().setDrawGridLines(false);
        mChartPrice.getAxisLeft().setDrawAxisLine(false);
        mChartPrice.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);

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

        //xAxisVolume.setValueFormatter(new KLineXValueFormatter(mData));


        mChartVolume.getAxisLeft().setDrawLabels(true);
        mChartVolume.getAxisLeft().setDrawGridLines(false);
        mChartVolume.getAxisLeft().setLabelCount(3, true);
        mChartVolume.getAxisLeft().setAxisMinimum(0);
        mChartVolume.getAxisLeft().setDrawAxisLine(false);
        mChartVolume.getAxisLeft().setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
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
        mChartPrice.setOnChartGestureListener(new CoupleChartGestureListener(mChartPrice, mChartVolume));
        mChartVolume.setOnChartGestureListener(new CoupleChartGestureListener(mChartVolume, mChartPrice));

        // TODO: 2018/1/28 取值显示
    }


    public void initChartKData(List<KlineEntity> list) {
        mData.clear();
        mData.addAll(KlineUtil.calculateKlineData(list));
        // 蜡烛图
        ArrayList<CandleEntry> candleEntries = new ArrayList<>();
        // 平均线
        ArrayList<Entry> lineAveEntries = new ArrayList<>();
        // MA 系列
        ArrayList<Entry> ma5Entries = new ArrayList<>();
        ArrayList<Entry> ma10Entries = new ArrayList<>();
        ArrayList<Entry> ma20Entries = new ArrayList<>();
        ArrayList<Entry> ma30Entries = new ArrayList<>();

        for (int i = 0; i < mData.size(); i++) {
            KlineEntity klineEntity = mData.get(i);
            candleEntries.add(new CandleEntry(i, (float) klineEntity.getHigh(), (float) klineEntity.getLow(), (float) klineEntity.getOpen(), (float) klineEntity.getClose()));
            lineAveEntries.add(new Entry(i, (float) klineEntity.getAvePrice()));
            ma5Entries.add(new Entry(i, (float) klineEntity.getMa5()));
            ma10Entries.add(new Entry(i, (float) klineEntity.getMa10()));
            ma20Entries.add(new Entry(i, (float) klineEntity.getMa20()));
            ma30Entries.add(new Entry(i, (float) klineEntity.getMa30()));
        }
        LineData lineData = new LineData(
                setLine(AVE_LINE, lineAveEntries),
                setLine(MA5, ma5Entries),
                setLine(MA10, ma10Entries),
                setLine(MA20, ma20Entries),
                setLine(MA30, ma30Entries));
        CandleData candleData = new CandleData(setKLine(NORMAL_LINE, candleEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(lineData);
        combinedData.setData(candleData);


        mChartPrice.setData(combinedData);
        mChartPrice.setVisibleXRange(MAX_COUNT_LINE, MIN_COUNT_LINE);
        mChartPrice.notifyDataSetChanged();
        mChartPrice.invalidate();
        mChartPrice.moveViewToX(combinedData.getEntryCount());
        initChartVolumeData();

    }

    private void initChartVolumeData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            KlineEntity klineEntity = mData.get(i);
            barEntries.add(new BarEntry(i, (float) klineEntity.getVol(), klineEntity));
        }

        BarData barData = new BarData(setBar(barEntries));
        CombinedData combinedData = new CombinedData();
        combinedData.setData(barData);
        mChartVolume.setData(combinedData);

        mChartVolume.setVisibleXRange(MAX_COUNT_LINE, MIN_COUNT_LINE);

        setOffset();
        mChartVolume.notifyDataSetChanged();
        mChartVolume.invalidate();
        mChartVolume.moveViewToX(combinedData.getEntryCount());


    }

    public void addKData(KlineEntity klineEntity) {
        try {
            lock.tryLock(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }
        klineEntity = KlineUtil.calculateHisData(klineEntity, mData);
        CombinedData combinedData = mChartPrice.getData();
        LineData priceData = combinedData.getLineData();
        ILineDataSet aveSet = priceData.getDataSetByIndex(0);
        ILineDataSet ma5Set = priceData.getDataSetByIndex(1);
        ILineDataSet ma10Set = priceData.getDataSetByIndex(2);
        ILineDataSet ma20Set = priceData.getDataSetByIndex(3);
        ILineDataSet ma30Set = priceData.getDataSetByIndex(4);
        CandleData kData = combinedData.getCandleData();
        ICandleDataSet kSet = kData.getDataSetByIndex(0);
        IBarDataSet volSet = mChartVolume.getData().getBarData().getDataSetByIndex(0);
        if (mData.contains(klineEntity)) {
            int index = mData.indexOf(klineEntity);
            kSet.removeEntry(index);
            aveSet.removeEntry(index);
            volSet.removeEntry(index);
            ma5Set.removeEntry(index);
            ma10Set.removeEntry(index);
            ma20Set.removeEntry(index);
            ma30Set.removeEntry(index);
            mData.remove(index);
        }
        mData.add(klineEntity);
        kSet.addEntry(new CandleEntry(kSet.getEntryCount(), (float) klineEntity.getHigh(), (float) klineEntity.getLow(), (float) klineEntity.getOpen(), (float) klineEntity.getClose()));
        aveSet.addEntry(new Entry(aveSet.getEntryCount(), (float) klineEntity.getAvePrice()));
        volSet.addEntry(new BarEntry(volSet.getEntryCount(), (float) klineEntity.getVol()));
        ma5Set.addEntry(new BarEntry(ma5Set.getEntryCount(), (float) klineEntity.getMa5()));
        ma10Set.addEntry(new BarEntry(ma10Set.getEntryCount(), (float) klineEntity.getMa10()));
        ma20Set.addEntry(new BarEntry(ma20Set.getEntryCount(), (float) klineEntity.getMa20()));
        ma30Set.addEntry(new BarEntry(ma30Set.getEntryCount(), (float) klineEntity.getMa30()));

        mChartPrice.setAutoScaleMinMaxEnabled(true);
        mChartVolume.setAutoScaleMinMaxEnabled(true);


        mChartPrice.getXAxis().setAxisMinimum(0);
        mChartPrice.getXAxis().setAxisMaximum(mData.size());
        mChartVolume.getXAxis().setAxisMinimum(0);
        mChartVolume.getXAxis().setAxisMaximum(mData.size());


        mChartPrice.notifyDataSetChanged();
        mChartVolume.notifyDataSetChanged();

        mChartPrice.invalidate();
        mChartVolume.invalidate();

        lock.unlock();
//
//        mChartPrice.notifyDataSetChanged();
//        mChartPrice.invalidate();
//        mChartVolume.notifyDataSetChanged();
//        mChartVolume.invalidate();


    }

    /**
     * 设置线
     *
     * @param type
     * @param lineEntries
     * @return
     */
    @android.support.annotation.NonNull
    private LineDataSet setLine(int type, ArrayList<Entry> lineEntries) {
        LineDataSet lineDataSetMa = new LineDataSet(lineEntries, "ma" + type);
        lineDataSetMa.setDrawValues(false);
        /*if (type == NORMAL_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.normal_line_color));
            lineDataSetMa.setCircleColor(ContextCompat.getColor(mContext, R.color.normal_line_color));
        } else*/
        if (type == AVE_LINE) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ave_color));
            lineDataSetMa.setCircleColor(android.R.color.transparent);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA5) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma5));
            lineDataSetMa.setCircleColor(android.R.color.transparent);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA10) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma10));
            lineDataSetMa.setCircleColor(android.R.color.transparent);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA20) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma20));
            lineDataSetMa.setCircleColor(android.R.color.transparent);
            lineDataSetMa.setHighlightEnabled(false);
        } else if (type == MA30) {
            lineDataSetMa.setColor(getResources().getColor(R.color.ma30));
            lineDataSetMa.setCircleColor(android.R.color.transparent);
            lineDataSetMa.setHighlightEnabled(false);
        } else {
            lineDataSetMa.setVisible(false);
            lineDataSetMa.setHighlightEnabled(false);
        }
        lineDataSetMa.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSetMa.setLineWidth(1f);
        lineDataSetMa.setCircleRadius(1f);

        lineDataSetMa.setDrawCircles(false);
        lineDataSetMa.setDrawCircleHole(false);

        return lineDataSetMa;
    }

    /**
     * 设置蜡烛图
     *
     * @param type
     * @param lineEntries
     * @return
     */
    @android.support.annotation.NonNull
    public CandleDataSet setKLine(int type, ArrayList<CandleEntry> lineEntries) {
        CandleDataSet set1 = new CandleDataSet(lineEntries, "KLine" + type);
        set1.setDrawIcons(false);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setShadowColor(Color.DKGRAY);
        set1.setShadowWidth(1f);

        set1.setDecreasingColor(ContextCompat.getColor(getContext(), R.color.decreasing_color));
        set1.setDecreasingPaintStyle(Paint.Style.FILL);
        set1.setShadowColorSameAsCandle(true);
        set1.setIncreasingColor(ContextCompat.getColor(getContext(), R.color.increasing_color));
        set1.setIncreasingPaintStyle(Paint.Style.FILL);
        set1.setNeutralColor(ContextCompat.getColor(getContext(), R.color.increasing_color));
        set1.setDrawValues(false);
        set1.setHighlightEnabled(true);
        return set1;
    }

    private BarDataSet setBar(ArrayList<BarEntry> barEntries) {
        BarDataSet barDataSet = new BarDataSet(barEntries, "vol");
        barDataSet.setHighLightAlpha(120);

        barDataSet.setHighLightColor(getResources().getColor(R.color.highlight_color));
        barDataSet.setDrawValues(false);
        barDataSet.setHighlightEnabled(true);
        barDataSet.setColors(getResources().getColor(R.color.increasing_color), getResources().getColor(R.color.decreasing_color));
        return barDataSet;
    }

    /**
     * align two chart
     */
    private void setOffset() {
        float lineLeft = mChartPrice.getViewPortHandler().offsetLeft();
        float barLeft = mChartVolume.getViewPortHandler().offsetLeft();
        float lineRight = mChartPrice.getViewPortHandler().offsetRight();
        float barRight = mChartVolume.getViewPortHandler().offsetRight();
        float offsetLeft, offsetRight;
        if (barLeft < lineLeft) {
            offsetLeft = Utils.convertPixelsToDp(lineLeft - barLeft);
            mChartVolume.setExtraLeftOffset(offsetLeft);
        } else {
            offsetLeft = Utils.convertPixelsToDp(barLeft - lineLeft);
            mChartPrice.setExtraLeftOffset(offsetLeft);
        }
        if (barRight < lineRight) {
            offsetRight = Utils.convertPixelsToDp(lineRight);
            mChartVolume.setExtraRightOffset(offsetRight);
        } else {
            offsetRight = Utils.convertPixelsToDp(barRight);
            mChartPrice.setExtraRightOffset(offsetRight);
        }

    }
}
