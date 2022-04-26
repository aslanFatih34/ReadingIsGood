package com.getir.readingisgood.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class OrderCreateRequest {
    @NotBlank
    private String bookName;
    @Min(value = 1, message = "Quantity should not be less than 1")
    private int quantity;
}
