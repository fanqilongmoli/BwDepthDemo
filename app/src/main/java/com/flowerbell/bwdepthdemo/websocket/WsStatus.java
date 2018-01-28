package com.flowerbell.bwdepthdemo.websocket;

/**
 * Created by fanqilong on 2018/1/9.
 */

public class WsStatus {

    public final static int CONNECTED = 1;
    public final static int CONNECTING = 0;
    public final static int RECONNECT = 2;
    public final static int DISCONNECTED = -1;

    class CODE {
        //正常关闭
        public final static int NORMAL_CLOSE = 1000;
        // 异常关闭
        public final static int ABNORMAL_CLOSE = 1001;
    }

    class TIP {

        public final static String NORMAL_CLOSE = "normal close";
        public final static String ABNORMAL_CLOSE = "abnormal close";

    }

}
