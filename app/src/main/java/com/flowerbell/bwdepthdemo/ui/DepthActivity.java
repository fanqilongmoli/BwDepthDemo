package com.flowerbell.bwdepthdemo.ui;

import android.os.Bundle;

import com.flowerbell.bwdepthdemo.BaseActivity;
import com.flowerbell.bwdepthdemo.R;
import com.flowerbell.bwdepthdemo.TempData;
import com.flowerbell.bwdepthdemo.depth.BwDepthView;

import butterknife.BindView;

public class DepthActivity extends BaseActivity {
    @BindView(R.id.bwDepthView)
    BwDepthView bwDepthView;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_depth;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitData() {
        bwDepthView.setData(TempData.Depth);
    }
}
