package com.flowerbell.bwdepthdemo;

import java.math.BigDecimal;

/**
 * Created by MIT on 2018/1/27.
 */

public class DepthEntity {
    //price(成交价), amount(成交量)
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal allAmount;

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }
}
