package com.usg.book.adapter.out.persistence;

import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Book;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookPersistenceAdapter implements BookPersistencePort {

    private final BookRepository bookRepository;

    @Override
    public Long registerBook(Book book) {
        BookEntity bookEntity = BookEntity
                .builder()
                .email(book.getEmail())
                .bookName(book.getBookName())
                .bookRealPrice(book.getBookRealPrice())
                .author(book.getAuthor())
                .publisher(book.getPublisher())
                .bookPostName(book.getBookPostName())
                .bookComment(book.getBookComment())
                .bookPrice(book.getBookPrice())
                .isbn(book.getIsbn())
                .build();

        BookEntity savedBookEntity = bookRepository.save(bookEntity);

        return savedBookEntity.getId();
    }

    @Override
    public BookEntity findById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("Book Not Exist")
        );
    }

    @Override
    public void deleteById(Long bookId){
        bookRepository.deleteById(bookId);
    }

    @Override
    public BookEntity save(BookEntity bookEntity){
        return bookRepository.save(bookEntity);
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable){
        return bookRepository.findAll(pageable);
    }
}
