package com.flowerbell.bwdepthdemo.widget.bwchat.renderer;

import android.graphics.Canvas;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIT on 2018/2/11.
 */

public class BwCombinedChartRenderer extends DataRenderer {
    /**
     * 所有的绘制 在 combined中 组合绘制
     */
    protected List<DataRenderer> mRenderers = new ArrayList<DataRenderer>(5);

    protected WeakReference<Chart> mChart;

    public BwCombinedChartRenderer(CombinedChart chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);

        mChart = new WeakReference<Chart>(chart);
        createRenderers();
    }

    private void createRenderers() {
        mRenderers.clear();
        CombinedChart combinedChart = (CombinedChart) mChart.get();
        if (combinedChart == null)
            return;
        //BAR 柱状, BUBBLE 泡泡, LINE 线, CANDLE 蜡烛, SCATTER 散射
        CombinedChart.DrawOrder[] drawOrder = combinedChart.getDrawOrder();
        for (CombinedChart.DrawOrder order : drawOrder) {
            switch (order) {
                case BAR:
                    break;
                case LINE:
                    break;
                case BUBBLE:
                    break;
                case CANDLE:
                    break;
                case SCATTER:
                    break;
            }
        }

    }

    @Override
    public void initBuffers() {

    }

    @Override
    public void drawData(Canvas c) {

    }

    @Override
    public void drawValues(Canvas c) {

    }

    @Override
    public void drawExtras(Canvas c) {

    }

    @Override
    public void drawHighlighted(Canvas c, Highlight[] indices) {

    }
}
