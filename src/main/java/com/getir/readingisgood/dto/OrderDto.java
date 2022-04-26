package com.getir.readingisgood.dto;

import com.getir.readingisgood.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderDto {

    private String customerName;
    private String bookName;
    private String address;

    private OrderStatus status;
    private int quantity;
}
