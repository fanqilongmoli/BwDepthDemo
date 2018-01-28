package com.flowerbell.bwdepthdemo.websocket.listener;

import okhttp3.Response;
import okio.ByteString;

/**
 * Created by MIT on 2018/1/12.
 */

public interface WsMessageListener {
    void onOpen(Response response);

    void onMessage(String text);

    void onReconnect();

    void onClosing(int code, String reason);

    void onClosed(int code, String reason);

    void onFailure(Throwable t, Response response);
}
