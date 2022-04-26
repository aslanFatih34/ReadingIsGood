package com.getir.readingisgood.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Month;

@Getter
@Setter
@ToString
public class StatisticDto {
    private Month month;
    private int totalOrderCount;
    private int totalBookCount;
    private BigDecimal totalPurchasedAmount;

    public StatisticDto(Month month) {
        this.month = month;
    }
}
