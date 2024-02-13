package com.usg.book.application.service;

import com.usg.book.application.port.in.BookRegisterCommend;
import com.usg.book.application.port.in.BookRegisterUseCase;
import com.usg.book.application.port.out.BookISBNCheckPort;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService implements BookRegisterUseCase {

    private final BookPersistencePort bookPersistencePort;
    private final BookISBNCheckPort bookISBNCheckPort;

    @Override
    @Transactional
    public Long registerBook(BookRegisterCommend commend) {

        // 중앙 도서 API 연동
        bookISBNCheckPort.bookIsbnCheck(commend.getIsbn(), commend.getBookRealPrice());

        // 책 데이터 저장
        Book book = commendToBook(commend);
        Long savedBookId = bookPersistencePort.registerBook(book);

        return savedBookId;
    }

    private Book commendToBook(BookRegisterCommend commend) {
        return Book
                .builder()
                .email(commend.getEmail())
                .bookName(commend.getBookName())
                .bookRealPrice(commend.getBookRealPrice())
                .author(commend.getAuthor())
                .publisher(commend.getPublisher())
                .bookPostName(commend.getBookPostName())
                .bookComment(commend.getBookComment())
                .bookPrice(commend.getBookPrice())
                .isbn(commend.getIsbn())
                .build();
    }
}
