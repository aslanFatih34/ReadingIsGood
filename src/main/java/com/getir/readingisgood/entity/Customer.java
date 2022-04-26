package com.getir.readingisgood.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String lastName;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
