package com.usg.book.adapter.out.persistence;

import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements BookPersistencePort {

    private final BookRepository bookRepository;

    @Override
    public Long registerBook(Book book) {
        BookEntity bookEntity = BookEntity
                .builder()
                .memberId(book.getMemberId())
                .bookName(book.getBookName())
                .bookCommend(book.getBookComment())
                .bookPostName(book.getBookPostName())
                .bookPrice(book.getBookPrice())
                .isbn(book.getIsbn())
                .build();

        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        return savedBookEntity.getId();
    }
}
