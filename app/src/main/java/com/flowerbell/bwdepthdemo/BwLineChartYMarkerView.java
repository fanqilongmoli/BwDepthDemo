package com.flowerbell.bwdepthdemo;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by MIT on 2018/1/27.
 */

public class BwLineChartYMarkerView extends MarkerView {
    private final int digits;
    private TextView tvContent;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param digits  the layout resource to use for the MarkerView
     */
    public BwLineChartYMarkerView(Context context, int digits) {
        super(context, R.layout.view_mp_real_price_marker);
        this.digits = digits;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float value = e.getY();
        // TODO: 2018/1/27 可以再这里修改 小数点保留
        tvContent.setText(value + "");
        super.refreshContent(e, highlight);

    }
}
