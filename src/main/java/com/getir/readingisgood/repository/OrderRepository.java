package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long> {
    Optional<Order> findByOrderId(String bookId);

    Page<Order> findAllByOrderByCreatedDateAsc(Pageable pageable);

    @Query(value = "select * from orders o " +
            "inner join customer c on c.id = o.customer_id " +
            "where c.customer_id =:customerId " +
            "order by o.created_date asc", nativeQuery = true)
    List<Order> findAllByCustomerOrderByCreatedDateAsc(@Param("customerId") String customerId);
}
