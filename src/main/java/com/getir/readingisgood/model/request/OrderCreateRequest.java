package com.getir.readingisgood.model.request;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@ToString
@Builder
public class OrderCreateRequest {
    @NotBlank
    private String bookName;
    @Min(value = 1, message = "Quantity should not be less than 1")
    private int quantity;
}
