package com.getir.readingisgood.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CustomerDto {

    private String customerId;
    private String name;
    private String lastName;
    private String email;
    private String address;
}
