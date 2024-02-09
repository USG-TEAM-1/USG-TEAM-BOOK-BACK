package com.usg.book.adapter.out.persistence;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Long memberId = 1L;
        String bookName = "bookName";
        String bookPostName = "bookPostName";
        String bookComment = "bookComment";
        Integer bookPrice = 10;
        String isbn = "isbn";
        Book book = createBook(memberId, bookName, bookPostName, bookComment, bookPrice, isbn);
        BookEntity bookEntity = createBookEntity(memberId, bookName, bookPostName, bookComment, bookPrice, isbn);

        // stub
        when(bookRepository.save(any(BookEntity.class))).thenReturn(bookEntity);

        // when
        Long savedBookId = bookPersistenceAdapter.registerBook(book);

        // then
        assertThat(savedBookId).isEqualTo(null);

    }

    private Book createBook(Long memberId,
                            String bookName,
                            String bookComment,
                            String bookPostName,
                            Integer bookPrice,
                            String isbn) {
        return Book
                .builder()
                .memberId(memberId)
                .bookName(bookName)
                .bookComment(bookComment)
                .bookPostName(bookPostName)
                .bookPrice(bookPrice)
                .isbn(isbn)
                .build();
    }

    private BookEntity createBookEntity(Long memberId,
                                        String bookName,
                                        String bookComment,
                                        String bookPostName,
                                        Integer bookPrice,
                                        String isbn) {
        return BookEntity
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
