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
    @DisplayName("책 엔티티를 데이터베이스에 저장한다.")
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
        assertThat(savedBook.getId()).isEqualTo(bookEntity.getId());
        assertThat(savedBook.getEmail()).isEqualTo(bookEntity.getEmail());
        assertThat(savedBook.getBookName()).isEqualTo(bookEntity.getBookName());
        assertThat(savedBook.getBookRealPrice()).isEqualTo(bookEntity.getBookRealPrice());
        assertThat(savedBook.getAuthor()).isEqualTo(bookEntity.getAuthor());
        assertThat(savedBook.getPublisher()).isEqualTo(bookEntity.getPublisher());
        assertThat(savedBook.getBookPostName()).isEqualTo(bookEntity.getBookPostName());
        assertThat(savedBook.getBookComment()).isEqualTo(bookEntity.getBookComment());
        assertThat(savedBook.getBookPrice()).isEqualTo(bookEntity.getBookPrice());
        assertThat(savedBook.getIsbn()).isEqualTo(bookEntity.getIsbn());
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
