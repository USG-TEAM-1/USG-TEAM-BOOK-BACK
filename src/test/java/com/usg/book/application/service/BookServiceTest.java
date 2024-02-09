package com.usg.book.application.service;

import com.usg.book.application.port.in.BookRegisterCommend;
import com.usg.book.application.port.out.BookISBNCheckPort;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;
    @Mock
    private BookPersistencePort bookPersistencePort;
    @Mock
    private BookISBNCheckPort bookISBNCheckPort;

    @Test
    @DisplayName("책 등록 서비스 테스트")
    void registerBookTest() {
        // given
        Long memberId = 1L;
        String bookName = "bookName";
        String bookComment = "bookComment";
        String bookPostName = "bookPostName";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookRegisterCommend bookRegisterCommend = createBookRegisterCommend(memberId, bookName, bookComment, bookPostName, bookPrice, isbn);

        // stub
        doNothing().when(bookISBNCheckPort).bookIsbnCheck(isbn);
        when(bookPersistencePort.registerBook(any(Book.class))).thenReturn(1L);

        // when
        Long savedBookId = bookService.registerBook(bookRegisterCommend);

        // then
        Assertions.assertThat(savedBookId).isEqualTo(1L);
    }

    private BookRegisterCommend createBookRegisterCommend(Long memberId,
                                        String bookName,
                                        String bookComment,
                                        String bookPostName,
                                        Integer bookPrice,
                                        String isbn) {
        return BookRegisterCommend
                .builder()
                .memberId(memberId)
                .bookName(bookName)
                .bookComment(bookComment)
                .bookPostName(bookPostName)
                .bookPrice(bookPrice)
                .isbn(isbn)
                .build();
    }
}
