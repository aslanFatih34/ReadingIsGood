package com.getir.readingisgood.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@Getter
@Setter
@ToString
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
}
