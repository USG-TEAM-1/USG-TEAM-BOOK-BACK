package com.usg.book.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.domain.Book;

public interface BookPersistencePort {

    Long registerBook(Book book);
    BookEntity findById(Long bookId);
    void deleteById(Long bookId);
    BookEntity save(BookEntity bookEntity);
    Page<BookEntity> findAll(Pageable pageable);
}
