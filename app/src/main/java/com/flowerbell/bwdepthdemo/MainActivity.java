package com.flowerbell.bwdepthdemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.flowerbell.bwdepthdemo.ui.DepthActivity;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitData() {

    }

    @OnClick(R.id.btn_depth)
    public void clickDepth(View view){
        Intent intent = new Intent(this,DepthActivity.class);
        startActivity(intent);
    }


}
