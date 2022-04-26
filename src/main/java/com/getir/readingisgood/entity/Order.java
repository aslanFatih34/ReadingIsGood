package com.getir.readingisgood.entity;

import com.getir.readingisgood.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

    @ManyToOne()
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne()
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;
}
