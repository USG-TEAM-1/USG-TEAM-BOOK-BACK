package com.usg.book.adapter.out.persistence;

import com.usg.book.IntegrationExternalApiMockingTestSupporter;
import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.domain.Image;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class BookImagePersistenceAdapterIntegrationTest extends IntegrationExternalApiMockingTestSupporter {

    @Autowired
    private BookImagePersistenceAdapter bookImagePersistenceAdapter;
    @Autowired
    private BookRepository bookRepository;

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
        Assertions.assertThat(savedBookImageId).isNotNull();
    }
}
