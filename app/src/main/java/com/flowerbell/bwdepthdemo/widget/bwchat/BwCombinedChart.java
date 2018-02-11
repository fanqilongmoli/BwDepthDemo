package com.flowerbell.bwdepthdemo.widget.bwchat;

import android.content.Context;
import android.util.AttributeSet;

import com.flowerbell.bwdepthdemo.widget.bwchat.renderer.BwCombinedChartRenderer;
import com.github.mikephil.charting.charts.CombinedChart;

/**
 * Created by MIT on 2018/2/11.
 * 重写组合view
 */

public class BwCombinedChart extends CombinedChart {

    public BwCombinedChart(Context context) {
        super(context);
    }

    public BwCombinedChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BwCombinedChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new BwCombinedChartRenderer(this,mAnimator,mViewPortHandler);
    }
}
