package com.flowerbell.bwdepthdemo.ui;

import android.os.Bundle;

import com.flowerbell.bwdepthdemo.BaseActivity;
import com.flowerbell.bwdepthdemo.R;
import com.flowerbell.bwdepthdemo.TempData;
import com.flowerbell.bwdepthdemo.depth.BwDepthView;
import com.flowerbell.bwdepthdemo.websocket.WsClient;
import com.flowerbell.bwdepthdemo.websocket.listener.WsMessageListener;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;

public class DepthActivity extends BaseActivity {
    @BindView(R.id.bwDepthView)
    BwDepthView bwDepthView;


    @Override
    protected int getLayoutResource() {
        return R.layout.activity_depth;
    }

    @Override
    protected void onInitView() {

        bwDepthView.setData(TempData.Depth);
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
                    if (jsonObject.has("rep")) {
                        String req = jsonObject.optString("rep");
                        if (req.equals("market.btcusdt.depth.step0")) {
                            bwDepthView.setData(text);


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

    }

    @OnClick(R.id.loadData)
    public void loadData(){
        String temp = "{\"req\": \"market.btcusdt.depth.step0\",\"id\": \"id10\"}";
        WsClient.getClient().getWsManager().getWebSocket().send(temp);
    }
}
