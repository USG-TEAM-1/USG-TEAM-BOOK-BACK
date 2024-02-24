package com.usg.book.adapter.out.persistence;

import com.usg.book.IntegrationExternalApiMockingTestSupporter;
import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.adapter.out.persistence.entity.ImageEntity;
import com.usg.book.adapter.out.persistence.entity.ImageRepository;
import com.usg.book.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
public class BookImagePersistenceAdapterIntegrationTest extends IntegrationExternalApiMockingTestSupporter {

    @Autowired
    private BookImagePersistenceAdapter bookImagePersistenceAdapter;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Test
    @DisplayName("어뎁터로 이미지를 DB에 저장한다.")
    void saveImageTest() {
        // given
        BookEntity bookEntity = BookEntity.builder().build();
        BookEntity savedBookEntity = bookRepository.save(bookEntity);
        MockMultipartFile imageFile
                = new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[10]);
        Image image = Image.builder()
                .bookId(savedBookEntity.getId())
                .storeFilename("storeFilename")
                .gcsUrl("gcsUrl")
                .image(imageFile)
                .build();

        // when
        Long savedBookImageId = bookImagePersistenceAdapter.saveImage(image, savedBookEntity);

        // then
        assertThat(savedBookImageId).isNotNull();
    }

    @Test
    @DisplayName("Book PK 로 이미지 엔티티들을 조회한다.")
    void getImagesUrlsTest() {
        // given
        BookEntity savedBookEntity = createBookEntity();
        ImageEntity imageEntity1 = createImageEntity(savedBookEntity);
        ImageEntity imageEntity2 = createImageEntity(savedBookEntity);
        ImageEntity imageEntity3 = createImageEntity(savedBookEntity);

        // when
        List<ImageEntity> imageEntities = bookImagePersistenceAdapter.getImagesByBookId(savedBookEntity.getId());

        // then
        assertThat(imageEntities).hasSize(3)
                .extracting("bookEntity")
                .contains(savedBookEntity, savedBookEntity, savedBookEntity);
    }

    private BookEntity createBookEntity() {
        BookEntity bookEntity = BookEntity.builder().build();
        return bookRepository.save(bookEntity);
    }

    private ImageEntity createImageEntity(BookEntity bookEntity) {
        MockMultipartFile imageFile
                = new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[10]);
        ImageEntity imageEntity = ImageEntity.builder()
                .uploadFilename(imageFile.getOriginalFilename())
                .storeFilename("storeFilename")
                .gcsUrl("gcsUrl")
                .bookEntity(bookEntity)
                .build();
        return imageRepository.save(imageEntity);
    }
}
