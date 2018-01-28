package com.flowerbell.bwdepthdemo.depth;

import com.github.mikephil.charting.data.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIT on 2018/1/27.
 */

public class DepthUtil {

    private float YMax, YMin, XMax, Xmin;

    private List<DepthEntity> buyDepthList;
    private List<DepthEntity> sellDepthList;

    // 解析数据
    public void depthParse(String json) {
        buyDepthList = new ArrayList<>();
        sellDepthList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject tick = jsonObject.optJSONObject("data");
            // 买盘
            JSONArray bids = tick.optJSONArray("bids");
            DepthEntity depthEntity;

            double tempBuyAllAmount = 0d;
            for (int i = 0; i < bids.length(); i++) {
                // y 价格  x 数量
                JSONArray jsonArray = bids.getJSONArray(i);
                double price = (double) jsonArray.optDouble(0);
                double amount = (double) jsonArray.optDouble(1);
                tempBuyAllAmount += amount;
                depthEntity = new DepthEntity();
                depthEntity.setPrice(new BigDecimal(price));
                depthEntity.setAmount(new BigDecimal(amount));
                depthEntity.setAllAmount(new BigDecimal(tempBuyAllAmount));
                buyDepthList.add(depthEntity);


            }
            //卖盘
            double tempSellAllAmount = 0d;
            JSONArray asks = tick.optJSONArray("asks");
            for (int i = 0; i < asks.length(); i++) {
                // y 价格  x 数量
                JSONArray jsonArray = asks.getJSONArray(i);
                double price = (double) jsonArray.optDouble(0);
                double amount = (double) jsonArray.optDouble(1);
                tempSellAllAmount += amount;
                depthEntity = new DepthEntity();
                depthEntity.setPrice(new BigDecimal(price));
                depthEntity.setAmount(new BigDecimal(amount));
                depthEntity.setAllAmount(new BigDecimal(tempSellAllAmount));
                sellDepthList.add(depthEntity);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public List<Entry> getBuyEntryList() {
        List<Entry> entries = new ArrayList<>();

        //买
        int size = buyDepthList.size();
        for (int i = size - 1; i >= 0; i--) {
            DepthEntity depthEntity = buyDepthList.get(i);
            BigDecimal price = depthEntity.getPrice();
            BigDecimal allAmount = depthEntity.getAllAmount();
            entries.add(new Entry(price.floatValue(), allAmount.floatValue(), depthEntity));
        }
        return entries;
    }
    public List<Entry> getSellEntryList() {
        List<Entry> entries = new ArrayList<>();

        //卖
        int size1 = sellDepthList.size();
        for (int i = 0; i < size1; i++) {
            DepthEntity depthEntity = sellDepthList.get(i);
            BigDecimal price = depthEntity.getPrice();
            BigDecimal allAmount = depthEntity.getAllAmount();
            entries.add(new Entry(price.floatValue(), allAmount.floatValue(), depthEntity));
        }

        return entries;
    }

    public float getYMax() {

        return YMax;
    }

    public float getYMin() {
        return YMin;
    }

    public float getXMax() {
        return XMax;
    }

    public float getXmin() {
        return Xmin;
    }
}
