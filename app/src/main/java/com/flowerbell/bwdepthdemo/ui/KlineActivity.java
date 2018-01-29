package com.flowerbell.bwdepthdemo.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.flowerbell.bwdepthdemo.BaseActivity;
import com.flowerbell.bwdepthdemo.R;
import com.flowerbell.bwdepthdemo.kline.BwKlineView;
import com.flowerbell.bwdepthdemo.kline.KlineEntity;
import com.flowerbell.bwdepthdemo.kline.KlineUtil;
import com.flowerbell.bwdepthdemo.websocket.WsClient;
import com.flowerbell.bwdepthdemo.websocket.listener.WsMessageListener;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import okhttp3.Response;

public class KlineActivity extends BaseActivity {
    @BindView(R.id.bwKlineView)
    BwKlineView bwKlineView;

    String sub = "{\"sub\": \"market.btcusdt.kline.1min\",\"id\": \"2222\"}";

    boolean sunSuccess = false;

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_kline;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitData() {

        WsClient.getClient().getWsManager().addWsMessageListener(new WsMessageListener() {
            @Override
            public void onOpen(Response response) {

            }

            @Override
            public void onMessage(String text) {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    String id = jsonObject.optString("id");

                    if (id.equals("1111")) {


                        List<KlineEntity> klineEntities = KlineUtil.KlineParse(jsonObject);
                        bwKlineView.initChartKData(klineEntities);
                        // 订阅最新
                        WsClient.getClient().getWsManager().sendMessage(sub);
                    } else if (id.equals("2222")) {
                        sunSuccess = true;
                        Toast.makeText(KlineActivity.this, "订阅成功", Toast.LENGTH_SHORT).show();
                    }

                    if (jsonObject.has("ch")) {
                        String ch = jsonObject.optString("ch");
                        if (ch.equals("market.btcusdt.kline.1min")&& sunSuccess) {
                            KlineEntity klineEntity = KlineUtil.KlineParseOne(jsonObject);
                            bwKlineView.addKData(klineEntity);
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onReconnect() {

            }

            @Override
            public void onClosing(int code, String reason) {

            }

            @Override
            public void onClosed(int code, String reason) {

            }

            @Override
            public void onFailure(Throwable t, Response response) {

            }
        });

        String req = "{\"req\": \"market.btcusdt.kline.1min\",\"id\": \"1111\"}";
        WsClient.getClient().getWsManager().sendMessage(req);
    }


}
