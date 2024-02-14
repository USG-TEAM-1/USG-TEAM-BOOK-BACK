package com.usg.book.application.service;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.application.port.in.BookRegisterCommend;
import com.usg.book.application.port.in.BookRegisterUseCase;
import com.usg.book.application.port.in.GetBookServiceResponse;
import com.usg.book.application.port.in.GetBookUseCase;
import com.usg.book.application.port.out.BookISBNCheckPort;
import com.usg.book.application.port.out.BookImagePersistencePort;
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
public class BookService implements BookRegisterUseCase, GetBookUseCase {

    private final BookPersistencePort bookPersistencePort;
    private final BookISBNCheckPort bookISBNCheckPort;
    private final BookImagePersistencePort bookImagePersistencePort;

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

    @Override
    public GetBookServiceResponse getBook(Long bookId) {
        BookEntity findBookEntity = bookPersistencePort.findById(bookId);
        String imageUrl = bookImagePersistencePort.getImageUrl(bookId);

        // kafka 를 이용해 닉네임 알아오기

        return GetBookServiceResponse
                .builder()
                .bookName(findBookEntity.getBookName())
                .bookComment(findBookEntity.getBookComment())
                .bookPostName(findBookEntity.getBookPostName())
                .bookPrice(findBookEntity.getBookPrice())
                .bookRealPrice(findBookEntity.getBookRealPrice())
//                .nickname()
                .imageUrl(imageUrl)
                .author(findBookEntity.getAuthor())
                .publisher(findBookEntity.getPublisher())
                .build();

    }
}
