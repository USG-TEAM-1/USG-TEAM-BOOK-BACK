package com.usg.book.adapter.out.persistence.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 리포지토리 책 등록 테스트")
    void saveTest() {
        // given
        String email = "email";
        String bookName = "bookName";
        String bookComment = "bookComment";
        String bookPostName = "bookPostName";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookEntity bookEntity = createBookEntity(email, bookName, bookComment, bookPostName, bookPrice, isbn);

        // when
        BookEntity savedBook = bookRepository.save(bookEntity);

        // then
        assertThat(savedBook).isEqualTo(bookEntity);
    }

    private BookEntity createBookEntity(String email,
                                        String bookName,
                                        String bookComment,
                                        String bookPostName,
                                        Integer bookPrice,
                                        String isbn) {
        return BookEntity
                .builder()
                .email(email)
                .bookName(bookName)
                .bookComment(bookComment)
                .bookPostName(bookPostName)
                .bookPrice(bookPrice)
                .isbn(isbn)
                .build();
    }
}
