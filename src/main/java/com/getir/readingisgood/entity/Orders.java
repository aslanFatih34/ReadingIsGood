package com.getir.readingisgood.entity;

import com.getir.readingisgood.enums.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
//@Table(name = "order")
@Getter
@Setter
@NoArgsConstructor
public class Orders implements Serializable {

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

    @CreationTimestamp
    private LocalDateTime createDateTime;

}
