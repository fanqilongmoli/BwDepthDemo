package com.flowerbell.bwdepthdemo.websocket;

import android.content.Context;


import com.flowerbell.bwdepthdemo.App;
import com.flowerbell.bwdepthdemo.AppConfig;
import com.flowerbell.bwdepthdemo.websocket.listener.WsStatusListener;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.ByteString;

/**
 * Created by MIT on 2018/1/12.
 */

public final class WsClient {

    private WsClient() {
    }

    private static WsClient client = null;
    private WsManager wsManager;


    public synchronized static WsClient getClient() {
        if (client == null) {
            client = new WsClient();
        }
        return client;

    }

    public synchronized WsManager getWsManager() {
        return wsManager;
    }

    public  void initWs(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .pingInterval(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        wsManager = new WsManager.Builder(context)
                .wsUrl(AppConfig.SOCKET_URL)
                .needReconnect(true)
                .client(okHttpClient)
                .build();

        wsManager.setWsStatusListener(new WsStatusListener() {
            @Override
            public void onOpen(Response response) {
                super.onOpen(response);

                Logger.d("onOpen======>" + response.message());
            }

            @Override
            public void onMessage(String text) {
                super.onMessage(text);
                Logger.d("onMessage===text===>" + text);
            }

            @Override
            public void onMessage(ByteString bytes) {
                super.onMessage(bytes);
                byte[] bytes1 = bytes.toByteArray();
                String s = uncompressZipBytesToString(bytes1, "utf-8");


                try {
                    JSONObject jsonObject = new JSONObject(s);
                    boolean ping = jsonObject.has("ping");
                    if (ping) {
                        wsManager.sendMessage("{\"pong\": " + jsonObject.optLong("ping") + "}");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Logger.d("onMessage===bytes===>" + s);

            }

            @Override
            public void onReconnect() {
                super.onReconnect();
                Logger.d("onReconnect======>");
            }

            @Override
            public void onClosing(int code, String reason) {
                super.onClosing(code, reason);
                Logger.d("onClosing======>code=" + code + ";reason=" + reason);
            }

            @Override
            public void onClosed(int code, String reason) {
                super.onClosed(code, reason);
                Logger.d("onClosed======>code=" + code + ";reason=" + reason);
            }

            @Override
            public void onFailure(Throwable t, Response response) {
                super.onFailure(t, response);
                Logger.d("onFailure======>Throwable=" + t.getMessage());
            }
        });
        wsManager.startConnect();
        //wsManager.stopConnect();
    }


    public static String uncompressZipBytesToString(byte[] bytes, String encoding) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            GZIPInputStream ungzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n;
            while ((n = ungzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            return out.toString(encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
