package com.usg.book.application.service;

import com.usg.book.IntegrationExternalApiMockingTestSupporter;
import com.usg.book.application.port.in.BookRegisterCommend;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;

public class BookServiceTest extends IntegrationExternalApiMockingTestSupporter {

    @Autowired
    private BookService bookService;
    @Autowired
    private BookPersistencePort bookPersistencePort;

    @Test
    @DisplayName("Commend 객체를 입력받아 책을 등록한다.")
    void registerBookTest() {
        // given
        BookRegisterCommend commend = BookRegisterCommend.builder()
                .email("email")
                .bookName("bookName")
                .bookRealPrice(30000)
                .author("author")
                .publisher("publisher")
                .bookPostName("bookPostName")
                .bookComment("bookComment")
                .bookPrice(28000)
                .isbn("isbn")
                .build();

        // stub
        doNothing().when(bookISBNCheckAdapter).bookIsbnCheck(anyString(), anyInt());

        // when
        Long savedBookId = bookService.registerBook(commend);

        // then
        Book findBook = bookPersistencePort.findBookById(savedBookId);
        assertThat(findBook.getBookId()).isEqualTo(savedBookId);
    }
}
