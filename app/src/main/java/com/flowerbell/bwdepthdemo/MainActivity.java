package com.flowerbell.bwdepthdemo;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


import com.flowerbell.bwdepthdemo.ui.DepthActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.btn_depth)
    public void clickDepth(View view){
        Intent intent = new Intent(this,DepthActivity.class);
        startActivity(intent);
    }


}
