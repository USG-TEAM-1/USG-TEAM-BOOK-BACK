package com.usg.book.adapter.out.persistence;

import com.usg.book.IntegrationExternalApiMockingTestSupporter;
import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.domain.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
public class BookPersistenceAdapterIntegrationTest extends IntegrationExternalApiMockingTestSupporter {

    @Autowired
    private BookPersistenceAdapter bookPersistenceAdapter;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("어뎁터를 이용해 책을 DB에 저장한다.")
    void registerBookTest() {
        // given
        Book book = createBook();

        // when
        Long savedBookId = bookPersistenceAdapter.registerBook(book);

        // then
        assertThat(savedBookId).isNotNull();
        BookEntity findBookEntity = bookRepository.findById(savedBookId).get();
        assertThat(findBookEntity).isNotNull();
        assertThat(findBookEntity.getId()).isEqualTo(savedBookId);
    }

    private Book createBook() {
        return Book.builder()
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
    }
}
