package com.flowerbell.bwdepthdemo.websocket;


import android.os.Handler;
import android.os.Looper;


import com.flowerbell.bwdepthdemo.AppConfig;
import com.flowerbell.bwdepthdemo.websocket.listener.SocketIOListener;
import com.orhanobut.logger.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import okhttp3.OkHttpClient;

/**
 * Created by MIT on 2018/1/15.
 */

public final class SocketIOClient {

    private static SocketIOClient instance;

    private Handler mainHandler = new Handler(Looper.getMainLooper());
    private final List<SocketIOListener> socketIOListeners = Collections.synchronizedList(new ArrayList<>());

    public static SocketIOClient getInstance() {
        if (instance == null) {
            instance = new SocketIOClient();
        }
        return instance;
    }

    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    private boolean isConnected;

    private SocketIOClient() {
    }


    public void init() {
        try {
            IO.Options opts = new IO.Options();
            opts.path = "/v1/market";
            opts.transports = new String[]{"websocket"};


//            opts.reconnection = true;// 是否自动重新连接
//            opts.reconnectionAttempts = 100; //重新连接尝试次数
//            opts.reconnectionDelayMax = 3000; //重新连接之间最长等待时间
//            opts.timeout = 20000; //connect_error和connect_timeout事件发出之前的等待时间

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .hostnameVerifier(new TrustAllHostnameVerifier())
                    .sslSocketFactory(createSSLSocketFactory())
                    .build();

            IO.setDefaultOkHttpWebSocketFactory(okHttpClient);
            IO.setDefaultOkHttpCallFactory(okHttpClient);
            socket = IO.socket(AppConfig.SOCKET_URL, opts);

            socket.on(Socket.EVENT_CONNECT, onConnect);
            socket.on(Socket.EVENT_DISCONNECT, onDisconnect);
            socket.on(Socket.EVENT_CONNECT_ERROR, onConnectError);
            socket.on(Socket.EVENT_CONNECT_TIMEOUT, onConnectError);
            socket.on(Socket.EVENT_RECONNECT_FAILED, onConnectFailed);
            // 深度图
            socket.on("topic_snapshot", topic_snapshot);
            // 市场价格
            socket.on("topic_prices", topic_prices);
            // 最新价格
            socket.on("topic_tick", topic_tick);
            // k先刷新
            socket.on("topic_bar", topic_bar);
            socket.on("history_bars", history_bars);
            // 链接
            socket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public void addMessageListener(SocketIOListener socketIOListener) {
        if (socketIOListener != null) {
            if (!this.socketIOListeners.contains(socketIOListener)) {
                this.socketIOListeners.add(socketIOListener);
            }
        }
    }

    public void removeMessageListener(SocketIOListener socketIOListener) {
        if (socketIOListener != null) {
            this.socketIOListeners.remove(socketIOListener);
        }
    }

    private Emitter.Listener topic_prices = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            Logger.e("socketIO====topic_prices=====", data.toString());

            if (socketIOListeners.size() > 0) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    mainHandler.post(() -> {
                        synchronized (SocketIOClient.this.socketIOListeners) {
                            for (SocketIOListener socketIOListener : socketIOListeners) {
                                socketIOListener.topicPrices(data);
                            }
                        }
                    });
                }
            } else {
                synchronized (SocketIOClient.this.socketIOListeners) {
                    for (SocketIOListener socketIOListener : socketIOListeners) {
                        socketIOListener.topicPrices(data);
                    }
                }
            }
        }
    };

    private Emitter.Listener topic_tick = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {


            if (args[0] instanceof JSONArray) {
                JSONArray data = (JSONArray) args[0];
                Logger.e("socketIO====topic_tick=====", data.toString());
            } else if (args[0] instanceof JSONObject) {
                JSONObject data = (JSONObject) args[0];
                Logger.e("socketIO====topic_tick=====", data.toString());
            }
            if (socketIOListeners.size() > 0) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    mainHandler.post(() -> {
                        synchronized (SocketIOClient.this.socketIOListeners) {
                            for (SocketIOListener socketIOListener : socketIOListeners) {
                                socketIOListener.topicTick(args[0]);
                            }
                        }
                    });
                }
            } else {
                synchronized (SocketIOClient.this.socketIOListeners) {
                    for (SocketIOListener socketIOListener : socketIOListeners) {
                        socketIOListener.topicTick(args[0]);
                    }
                }
            }
        }
    };

    private Emitter.Listener topic_bar = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            Logger.e("socketIO====topic_bar=====", data.toString());
            if (socketIOListeners.size() > 0) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    mainHandler.post(() -> {
                        synchronized (SocketIOClient.this.socketIOListeners) {
                            for (SocketIOListener socketIOListener : socketIOListeners) {
                                socketIOListener.topicBar(data);
                            }
                        }
                    });
                }
            } else {
                synchronized (SocketIOClient.this.socketIOListeners) {
                    for (SocketIOListener socketIOListener : socketIOListeners) {
                        socketIOListener.topicBar(data);
                    }
                }
            }
        }
    };
    private Emitter.Listener topic_snapshot = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            JSONObject data = (JSONObject) args[0];
            Logger.e("socketIO====topic_snapshot=====", data.toString());
            if (socketIOListeners.size() > 0) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    mainHandler.post(() -> {
                        synchronized (SocketIOClient.this.socketIOListeners) {
                            for (SocketIOListener socketIOListener : socketIOListeners) {
                                socketIOListener.topicSnapshot(data);
                            }
                        }
                    });
                }
            } else {
                synchronized (SocketIOClient.this.socketIOListeners) {
                    for (SocketIOListener socketIOListener : socketIOListeners) {
                        socketIOListener.topicSnapshot(data);
                    }
                }
            }
        }
    };

    private Emitter.Listener history_bars = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (args[0] instanceof String) {
                Logger.e("socketIO====history_bars=====", (String) args[0]);
            }else if (args[0] instanceof JSONObject){
                JSONObject data = (JSONObject) args[0];
                Logger.e("socketIO====history_bars=====", data.toString());
            }

//            if (socketIOListeners.size() > 0) {
//                if (Looper.myLooper() != Looper.getMainLooper()) {
//                    mainHandler.post(() -> {
//                        synchronized (SocketIOClient.this.socketIOListeners) {
//                            for (SocketIOListener socketIOListener : socketIOListeners) {
//                                socketIOListener.topicSnapshot(data);
//                            }
//                        }
//                    });
//                }
//            } else {
//                synchronized (SocketIOClient.this.socketIOListeners) {
//                    for (SocketIOListener socketIOListener : socketIOListeners) {
//                        socketIOListener.topicSnapshot(data);
//                    }
//                }
//            }
        }
    };


    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.e("socketIO====连接=====");
            isConnected = true;
        }
    };

    private Emitter.Listener onDisconnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            Logger.e("socketIO====断开连接=====");
            isConnected = false;
        }
    };

    private Emitter.Listener onConnectError = new Emitter.Listener() {
        @Override
        public void call(Object... args) {

            Logger.e("socketIO====连接错误=====");
        }
    };

    private Emitter.Listener onConnectFailed = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject arg = (JSONObject) args[0];
            Logger.e("socketIO====连接失败=====");
        }
    };

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }

    private static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    private static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}
