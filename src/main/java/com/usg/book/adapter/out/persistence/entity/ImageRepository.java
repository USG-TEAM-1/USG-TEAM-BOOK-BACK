package com.usg.book.adapter.out.persistence.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Long> {
    List<ImageEntity> findByBookEntity(BookEntity book);

    @Query("select i from ImageEntity i" +
            " where i.bookEntity.id = :bookId")
    ImageEntity findByBookId(@Param("bookId") Long bookId);
}
