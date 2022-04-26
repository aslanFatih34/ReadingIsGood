package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Orders;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Orders, Long> {
}
