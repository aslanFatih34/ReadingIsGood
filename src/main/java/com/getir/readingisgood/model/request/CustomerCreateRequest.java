package com.getir.readingisgood.model.request;

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
    @NotBlank
    private String lastName;
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String address;

    @Override
    public String toString() {
        return "CustomerCreateRequest{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
