package com.flowerbell.bwdepthdemo.websocket;

import okhttp3.WebSocket;
import okio.ByteString;

/**
 * Created by fanqilong on 2018/1/9.
 */

public interface IWsManager {

    WebSocket getWebSocket();

    void startConnect();

    void stopConnect();

    boolean isWsConnected();

    int getCurrentStatus();

    void setCurrentStatus(int currentStatus);

    boolean sendMessage(String msg);

    boolean sendMessage(ByteString byteString);
}
