package com.flowerbell.bwdepthdemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.flowerbell.bwdepthdemo.R;
import com.flowerbell.bwdepthdemo.depth.DepthEntity;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import java.util.List;

/**
 * Created by MIT on 2018/1/27.
 */

public class BwLineChartXMarkerView extends MarkerView {

    private List<DepthEntity> mList;
    private TextView tvContent;

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param list
     */
    public BwLineChartXMarkerView(Context context,@Nullable List<DepthEntity> list) {
        super(context, R.layout.view_mp_real_price_marker);
        //mList = list;
        tvContent = (TextView) findViewById(R.id.tvContent);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        float x = e.getX();
        tvContent.setText(String.valueOf(x));

        super.refreshContent(e, highlight);
    }

}
