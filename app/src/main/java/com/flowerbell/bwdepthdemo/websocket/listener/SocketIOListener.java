package com.flowerbell.bwdepthdemo.websocket.listener;

import org.json.JSONObject;

/**
 * Created by MIT on 2018/1/16.
 */

public interface SocketIOListener {

    void topicPrices(JSONObject jsonObject);

    void topicTick(Object o);

    void topicBar(JSONObject jsonObject);

    void topicSnapshot(JSONObject jsonObject);
}
