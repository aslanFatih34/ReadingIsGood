package com.getir.readingisgood.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    private String customerId;
    private String name;
    private String email;
    private String encryptedPassword;
}
