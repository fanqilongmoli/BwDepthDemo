package com.flowerbell.bwdepthdemo;

import android.app.Application;

import com.flowerbell.bwdepthdemo.websocket.WsClient;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by MIT on 2018/1/28.
 */

public class App extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
        WsClient.getClient().initWs(this);
    }
}

