package com.flowerbell.bwdepthdemo.kline;

/**
 * Created by MIT on 2018/1/29.
 */

public class KlineEntity {

    private double close;
    private double high;
    private double low;
    private double open;
    private double vol;
    private long date;
    private double amountVol;
    private double avePrice;
    private double total;
    private double maSum;
    private double ma5;
    private double ma10;
    private double ma20;
    private double ma30;

    public KlineEntity() {
    }

    public KlineEntity(double close, double high, double low, double open, int vol, long date) {
        this.close = close;
        this.high = high;
        this.low = low;
        this.open = open;
        this.vol = vol;
        this.date = date;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getVol() {
        return vol;
    }

    public void setVol(double vol) {
        this.vol = vol;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public double getAmountVol() {
        return amountVol;
    }

    public void setAmountVol(double amountVol) {
        this.amountVol = amountVol;
    }

    public double getAvePrice() {
        return avePrice;
    }

    public void setAvePrice(double avePrice) {
        this.avePrice = avePrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getMaSum() {
        return maSum;
    }

    public void setMaSum(double maSum) {
        this.maSum = maSum;
    }

    public double getMa5() {
        return ma5;
    }

    public void setMa5(double ma5) {
        this.ma5 = ma5;
    }

    public double getMa10() {
        return ma10;
    }

    public void setMa10(double ma10) {
        this.ma10 = ma10;
    }

    public double getMa20() {
        return ma20;
    }

    public void setMa20(double ma20) {
        this.ma20 = ma20;
    }

    public double getMa30() {
        return ma30;
    }

    public void setMa30(double ma30) {
        this.ma30 = ma30;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        KlineEntity klineEntity = (KlineEntity) obj;

        return date == klineEntity.getDate();
    }

    @Override
    public int hashCode() {
        return (int) (date ^ (date >>> 32));
    }

    @Override
    public String toString() {
        return "HisData{" +
                "close=" + close +
                ", high=" + high +
                ", low=" + low +
                ", open=" + open +
                ", vol=" + vol +
                ", date=" + date +
                ", amountVol=" + amountVol +
                ", avePrice=" + avePrice +
                ", total=" + total +
                ", maSum=" + maSum +
                ", ma5=" + ma5 +
                ", ma10=" + ma10 +
                ", ma20=" + ma20 +
                ", ma30=" + ma30 +
                '}';
    }
}
