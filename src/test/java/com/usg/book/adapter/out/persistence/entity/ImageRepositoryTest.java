package com.usg.book.adapter.out.persistence.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("이미지 저장 테스트")
    void saveTest() {
        // given
        String email = "email";
        String bookName = "bookName";
        String bookComment = "bookComment";
        String bookPostName = "bookPostName";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookEntity bookEntity = createBookEntity(email, bookName, bookComment, bookPostName, bookPrice, isbn);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        String uploadFilename = "uploadFilename";
        String storeFilename = "storeFilename";
        String gcsUrl = "gcsUrl";

        ImageEntity imageEntity = createImageEntity(uploadFilename, storeFilename, gcsUrl, savedBookEntity);

        // when
        ImageEntity savedImageEntity = imageRepository.save(imageEntity);

        // then
        assertThat(savedImageEntity).isEqualTo(imageEntity);
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

    private ImageEntity createImageEntity(String uploadFilename,
                                          String storeFilename,
                                          String gcsUrl,
                                          BookEntity bookEntity) {
        return ImageEntity
                .builder()
                .uploadFilename(uploadFilename)
                .storeFilename(storeFilename)
                .gcsUrl(gcsUrl)
                .bookEntity(bookEntity)
                .build();
    }

    @Test
    @DisplayName("책 PK 로 이미지 조회 테스트")
    void findByBookIdTest() {
        // given
        String email = "email";
        String bookName = "bookName";
        String bookComment = "bookComment";
        String bookPostName = "bookPostName";
        Integer bookPrice = 10;
        String isbn = "isbn";
        BookEntity bookEntity = createBookEntity(email, bookName, bookComment, bookPostName, bookPrice, isbn);
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        String uploadFilename = "uploadFilename";
        String storeFilename = "storeFilename";
        String gcsUrl = "gcsUrl";
        ImageEntity imageEntity = createImageEntity(uploadFilename, storeFilename, gcsUrl, savedBookEntity);
        ImageEntity savedImageEntity = imageRepository.save(imageEntity);

        // when
        List<ImageEntity> findImageEntities = imageRepository.findByBookId(savedBookEntity.getId());

        // then
        Assertions.assertThat(findImageEntities.get(0)).isEqualTo(savedImageEntity);
    }
}
