package com.usg.book.adapter.out.persistence.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void saveTest() {
        // given
        Long memberId = 1L;
        String bookName = "bookName";
        String bookCommend = "bookCommend";
        String bookPostName = "bookPostName";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookEntity bookEntity = createBookEntity(memberId, bookName, bookCommend, bookPostName, bookPrice, isbn);

        // when
        BookEntity savedBook = bookRepository.save(bookEntity);

        // then
        assertThat(savedBook).isEqualTo(bookEntity);
    }

    private BookEntity createBookEntity(Long memberId,
                                        String bookName,
                                        String bookCommend,
                                        String bookPostName,
                                        Integer bookPrice,
                                        String isbn) {
        return BookEntity
                .builder()
                .memberId(memberId)
                .bookName(bookName)
                .bookCommend(bookCommend)
                .bookPostName(bookPostName)
                .bookPrice(bookPrice)
                .isbn(isbn)
                .build();
    }
}
