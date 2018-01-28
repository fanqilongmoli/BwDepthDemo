package com.flowerbell.bwdepthdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.flowerbell.bwdepthdemo.R;
import com.flowerbell.bwdepthdemo.TempData;
import com.flowerbell.bwdepthdemo.depth.BwDepthView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DepthActivity extends AppCompatActivity {
    @BindView(R.id.bwDepthView)
    BwDepthView bwDepthView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depth);
        ButterKnife.bind(this);



        bwDepthView.setData(TempData.Depth);
    }
}
