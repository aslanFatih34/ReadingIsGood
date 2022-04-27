package com.getir.readingisgood.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookCreateRequest {
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private BigDecimal price;
    private int stock;

}
