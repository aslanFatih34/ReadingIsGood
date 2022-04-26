package com.getir.readingisgood.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
//@Table(name = "customer")
@Getter
@Setter
@NoArgsConstructor
public class Customer implements Serializable {

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

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @OneToMany(mappedBy = "customer")
    private List<Orders> orders;

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
