package com.flowerbell.bwdepthdemo.widget;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.List;

/**
 * Created by MIT on 2018/1/27.
 */

public class BwLineChartRenderer extends LineChartRenderer {

    private float[] mCirclesBuffer = new float[2];

    public BwLineChartRenderer(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawExtras(Canvas c) {
        super.drawExtras(c);

    }

    protected void drawLastPointCircle(Canvas c) {

        mRenderPaint.setStyle(Paint.Style.FILL);
        float phaseY = mAnimator.getPhaseY();
        mCirclesBuffer[0] = 0;
        mCirclesBuffer[1] = 0;

        List<ILineDataSet> dataSets = mChart.getLineData().getDataSets();

        for (int i = 0; i < dataSets.size(); i++) {

            ILineDataSet dataSet = dataSets.get(i);

            if (!dataSet.isVisible() /*|| !dataSet.isDrawCirclesEnabled()*/ ||
                    dataSet.getEntryCount() == 0)
                continue;

            mRenderPaint.setColor(dataSet.getCircleColor(0));
            mCirclePaintInner.setColor(dataSet.getCircleHoleColor());

            Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

            mXBounds.set(mChart, dataSet);

            float circleRadius = dataSet.getCircleRadius() * 2.0f;
            float circleHoleRadius = dataSet.getCircleHoleRadius() * 2.0f;
            boolean drawCircleHole = dataSet.isDrawCircleHoleEnabled() &&
                    circleHoleRadius < circleRadius &&
                    circleHoleRadius > 0.f;

            Entry e = dataSet.getEntryForIndex(dataSet.getEntryCount() - 1);

            if (e == null) return;

            mCirclesBuffer[0] = e.getX();
            mCirclesBuffer[1] = e.getY() * phaseY;

            trans.pointValuesToPixel(mCirclesBuffer);

            if (!mViewPortHandler.isInBoundsRight(mCirclesBuffer[0]))
                return;

            if (!mViewPortHandler.isInBoundsLeft(mCirclesBuffer[0]) ||
                    !mViewPortHandler.isInBoundsY(mCirclesBuffer[1]))
                return;

            c.drawCircle(
                    mCirclesBuffer[0],
                    mCirclesBuffer[1],
                    circleRadius,
                    mRenderPaint);

            if (drawCircleHole) {
                c.drawCircle(
                        mCirclesBuffer[0],
                        mCirclesBuffer[1],
                        circleHoleRadius,
                        mCirclePaintInner);
            }
        }
    }
}
