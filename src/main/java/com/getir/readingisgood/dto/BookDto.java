package com.getir.readingisgood.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookDto {

    private String bookId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;
}
