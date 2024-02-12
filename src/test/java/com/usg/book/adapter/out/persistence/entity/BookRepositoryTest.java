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
        Integer bookRealPrice = 100;
        String author = "author";
        String publisher = "publisher";
        String bookPostName = "bookPostName";
        String bookComment = "bookComment";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookEntity bookEntity = createBookEntity(email, bookName, bookRealPrice, author, publisher, bookPostName, bookComment, bookPrice, isbn);

        // when
        BookEntity savedBook = bookRepository.save(bookEntity);

        // then
        assertThat(savedBook).isEqualTo(bookEntity);
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
