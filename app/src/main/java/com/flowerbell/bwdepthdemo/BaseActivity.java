package com.flowerbell.bwdepthdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by MIT on 2018/1/28.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutResource() != 0) {
            setContentView(getLayoutResource());
            ButterKnife.bind(this);
        }

        onInitView();
        onInitData();

    }

    protected abstract int getLayoutResource();
    protected abstract void onInitView();
    protected abstract void onInitData();
}
