package com.usg.book.adapter.out.persistence;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.domain.Book;
import org.assertj.core.api.AbstractThrowableAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookPersistenceAdapterTest {

    @InjectMocks
    private BookPersistenceAdapter bookPersistenceAdapter;
    @Mock
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 영속성 어뎁터 책 등록 테스트")
    void registerBookTest() {
        // given
        String email = "email";
        String bookName = "bookName";
        Integer bookRealPrice = 100;
        String author = "author";
        String publisher = "publisher";
        String bookPostName = "bookPostName";
        String bookComment = "bookComment";
        Integer bookPrice = 10;
        String isbn = "isbn";
        Book book = createBook(email, bookName, bookRealPrice, author, publisher, bookPostName, bookComment, bookPrice, isbn);
        BookEntity bookEntity = createBookEntity(email, bookName, bookRealPrice, author, publisher, bookPostName, bookComment, bookPrice, isbn);

        // stub
        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        // when
        Long savedBookId = bookPersistenceAdapter.registerBook(book);

        // then
        assertThat(savedBookId).isEqualTo(null);

    }

    private Book createBook(String email,
                            String bookName,
                            Integer bookRealPrice,
                            String author,
                            String publisher,
                            String bookComment,
                            String bookPostName,
                            Integer bookPrice,
                            String isbn) {
        return Book
                .builder()
                .email(email)
                .bookName(bookName)
                .bookRealPrice(bookRealPrice)
                .author(author)
                .publisher(publisher)
                .bookPostName(bookPostName)
                .bookComment(bookComment)
                .bookPrice(bookPrice)
                .isbn(isbn)
                .build();
    }

    @Test
    @DisplayName("PK 로 책 엔티티 조회 테스트")
    void findByIdTest() {
        // given
        Long bookId = 1L;
        String email = "email";
        String bookName = "bookName";
        Integer bookRealPrice = 100;
        String author = "author";
        String publisher = "publisher";
        String bookPostName = "bookPostName";
        String bookComment = "bookComment";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookEntity bookEntity = createBookEntity(email, bookName, bookRealPrice, author, publisher, bookPostName, bookComment, bookPrice, isbn);

        // stub
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookEntity));

        // when
        BookEntity findBookEntity = bookPersistenceAdapter.findById(bookId);

        // then
        assertThat(findBookEntity).isEqualTo(bookEntity);
    }

    @Test
    @DisplayName("PK 로 책 엔티티 조회 실패 테스트")
    void findByIdFailTest() {
        // given
        Long bookId = 1L;

        // stub
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        // when
        AbstractThrowableAssert<?, ? extends Throwable> abstractThrowableAssert
                = assertThatThrownBy(() -> bookPersistenceAdapter.findById(bookId));

        // then
        abstractThrowableAssert
                .isInstanceOf(IllegalArgumentException.class);
    }

    private BookEntity createBookEntity(String email,
                                        String bookName,
                                        Integer bookRealPrice,
                                        String author,
                                        String publisher,
                                        String bookComment,
                                        String bookPostName,
                                        Integer bookPrice,
                                        String isbn) {
        return BookEntity
                .builder()
                .email(email)
                .bookName(bookName)
                .bookRealPrice(bookRealPrice)
                .author(author)
                .publisher(publisher)
                .bookPostName(bookPostName)
                .bookComment(bookComment)
                .bookPrice(bookPrice)
                .isbn(isbn)
                .build();
    }
}
