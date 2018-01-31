package com.flowerbell.bwdepthdemo.kline;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIT on 2018/1/29.
 */

public class KlineUtil {

    /**
     * 数据解析
     *
     * @param jsonObject
     * @return
     */
    public static List<KlineEntity> KlineParse(JSONObject jsonObject) {
        List<KlineEntity> klineEntities = new ArrayList<>();
        KlineEntity klineEntity;
        JSONArray tickArray = jsonObject.optJSONArray("data");
        int length = tickArray.length();
        for (int i = 0; i < length; i++) {

//                    "id": K线id,
//                    "amount": 成交量,
//                    "count": 成交笔数,
//                    "open": 开盘价,
//                    "close": 收盘价,当K线为最晚的一根时，是最新成交价
//                    "low": 最低价,
//                    "high": 最高价,
//                    "vol": 成交额, 即 sum(每一笔成交价 * 该笔的成交量)
            JSONObject object = tickArray.optJSONObject(i);
            klineEntity = new KlineEntity();
            klineEntity.setDate(object.optLong("id"));
            klineEntity.setOpen(object.optDouble("open"));
            klineEntity.setClose(object.optDouble("close"));
            klineEntity.setLow(object.optDouble("low"));
            klineEntity.setHigh(object.optDouble("high"));
            klineEntity.setVol(object.optDouble("amount"));

            klineEntities.add(klineEntity);
        }
        return klineEntities;
    }

    public static KlineEntity KlineParseOne(JSONObject jsonObject) {
        KlineEntity klineEntity = new KlineEntity();
        JSONObject object = jsonObject.optJSONObject("tick");
        klineEntity.setDate(object.optLong("id"));
        klineEntity.setOpen(object.optDouble("open"));
        klineEntity.setClose(object.optDouble("close"));
        klineEntity.setLow(object.optDouble("low"));
        klineEntity.setHigh(object.optDouble("high"));
        klineEntity.setVol(object.optDouble("amount"));
        return klineEntity;

    }


    public static List<KlineEntity> calculateKlineData(List<KlineEntity> list) {
        return calculateKlineData(list, null);
    }

    public static List<KlineEntity> calculateKlineData(List<KlineEntity> list, KlineEntity lastData) {
        // Ma
        List<Double> ma5List = calculateMA(5, list);
        List<Double> ma10List = calculateMA(10, list);
        List<Double> ma20List = calculateMA(20, list);
        List<Double> ma30List = calculateMA(30, list);

        // 交易总量
        double amountVol = 0;
        if (lastData != null) {
            amountVol = lastData.getAmountVol();
        }

        for (int i = 0; i < list.size(); i++) {
            KlineEntity klineEntity = list.get(i);

            klineEntity.setMa5(ma5List.get(i));
            klineEntity.setMa10(ma10List.get(i));
            klineEntity.setMa20(ma20List.get(i));
            klineEntity.setMa30(ma30List.get(i));

            amountVol += klineEntity.getVol();
            klineEntity.setAmountVol(amountVol);
            if (i > 0) {
                // 总价
                double total = klineEntity.getVol() * klineEntity.getClose() + list.get(i - 1).getTotal();
                klineEntity.setTotal(total);
                // 平均价
                double avePrice = total / amountVol;
                klineEntity.setAvePrice(avePrice);

            } else if (lastData != null) {

                double total = klineEntity.getVol() * klineEntity.getClose() + lastData.getTotal();
                klineEntity.setTotal(total);
                // 平均价
                double avePrice = total / amountVol;
                klineEntity.setAvePrice(avePrice);
            } else {
                klineEntity.setAmountVol(klineEntity.getVol());
                klineEntity.setAvePrice(klineEntity.getClose());
                klineEntity.setTotal(klineEntity.getAmountVol() * klineEntity.getAvePrice());
            }
        }
        return list;
    }

    /**
     * 根据历史数据 计算一个新的数据
     */
    public static KlineEntity calculateHisData(KlineEntity newData, List<KlineEntity> klineEntities) {

        KlineEntity lastData = klineEntities.get(klineEntities.size() - 1);
        double amountVol = lastData.getAmountVol();

        newData.setMa5(calculateLastMA(5, klineEntities));
        newData.setMa10(calculateLastMA(10, klineEntities));
        newData.setMa20(calculateLastMA(20, klineEntities));
        newData.setMa30(calculateLastMA(30, klineEntities));

        amountVol += newData.getVol();
        newData.setAmountVol(amountVol);

        double total = newData.getVol() * newData.getClose() + lastData.getTotal();
        newData.setTotal(total);
        double avePrice = total / amountVol;
        newData.setAvePrice(avePrice);

        return newData;
    }


    /**
     * calculate MA value, return a double list
     *
     * @param dayCount for example: 5, 10, 20, 30
     */
    public static List<Double> calculateMA(int dayCount, List<KlineEntity> data) {
        List<Double> result = new ArrayList<>(data.size());
        for (int i = 0, len = data.size(); i < len; i++) {
            if (i < dayCount) {
                result.add(Double.NaN);
                continue;
            }
            double sum = 0;
            for (int j = 0; j < dayCount; j++) {
                sum += data.get(i - j).getOpen();
            }
            result.add(+(sum / dayCount));
        }
        return result;
    }

    /**
     * calculate last MA value, return a double value
     */
    public static double calculateLastMA(int dayCount, List<KlineEntity> data) {
        double result = Double.NaN;
        for (int i = 0, len = data.size(); i < len; i++) {
            if (i < dayCount) {
                result = Double.NaN;
                continue;
            }
            double sum = 0;
            for (int j = 0; j < dayCount; j++) {
                sum += data.get(i - j).getOpen();
            }
            result = (+(sum / dayCount));
        }
        return result;
    }

}
