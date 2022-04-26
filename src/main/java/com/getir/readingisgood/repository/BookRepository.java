package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Book;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Book findByBookId(String bookId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Book findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "update Book b set b.stock=:stock where b.name =:name")
    void updateStock(@Param("stock") int stock, @Param("name") String name);
}
