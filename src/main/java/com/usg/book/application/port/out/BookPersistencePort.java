package com.usg.book.application.port.out;

import com.usg.book.domain.Book;

public interface BookPersistencePort {

    Long registerBook(Book bok);
}
