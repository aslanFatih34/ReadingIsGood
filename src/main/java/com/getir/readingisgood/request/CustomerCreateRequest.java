package com.getir.readingisgood.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
public class CustomerCreateRequest {
    @NotBlank
    private String name;
    @Email
    private String email;
}
