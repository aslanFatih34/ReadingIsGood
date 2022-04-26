package com.getir.readingisgood.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BookCreateRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private BigDecimal price;
    private int stock;

}
