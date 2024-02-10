package com.usg.book.application.port.out;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.domain.Book;

public interface BookPersistencePort {

    Long registerBook(Book book);
    BookEntity findById(Long bookId);
}
