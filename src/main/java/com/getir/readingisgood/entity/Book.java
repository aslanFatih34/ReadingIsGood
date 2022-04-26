package com.getir.readingisgood.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "book_uuid", nullable = false, unique = true)
    private String bookId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private int stock;

    @OneToMany(mappedBy = "book")
    private List<Order> orders;

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookId='" + bookId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
