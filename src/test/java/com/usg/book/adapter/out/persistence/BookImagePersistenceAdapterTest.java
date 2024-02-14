package com.usg.book.adapter.out.persistence;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.ImageEntity;
import com.usg.book.adapter.out.persistence.entity.ImageRepository;
import com.usg.book.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookImagePersistenceAdapterTest {

    @InjectMocks
    private BookImagePersistenceAdapter bookImagePersistenceAdapter;
    @Mock
    private ImageRepository imageRepository;

    @Test
    @DisplayName("이미지 DB 저장 어뎁터 테스트")
    void saveImageTest() {
        // given
        BookEntity bookEntity = BookEntity.builder().build();
        MockMultipartFile imageFile
                = new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[0]);
        Image image = Image.builder()
                .bookId(null)
                .storeFilename("storeFilename")
                .gcsUrl("gcsUrl")
                .image(imageFile)
                .build();

        // stub
        when(imageRepository.save(any(ImageEntity.class))).thenAnswer(invocation -> {
            ImageEntity imageEntity = invocation.getArgument(0);
            setPrivateField(imageEntity, "id", 1L);  // Reflection으로 ID 값을 설정
            return imageEntity;
        });

        // when
        Long savedImageEntityId = bookImagePersistenceAdapter.saveImage(image, bookEntity);

        // then
        assertThat(savedImageEntityId).isEqualTo(1L);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    @Test
    @DisplayName("책 PK 로 이미지 URL 조회 테스트")
    void getImageUrlTest() {
        // given
        Long bookId = 1L;
        String gcsUrl = "gcsUrl";
        ImageEntity imageEntity = ImageEntity.builder().gcsUrl(gcsUrl).build();

        // stub
        when(imageRepository.findByBookId(bookId)).thenReturn(imageEntity);

        // when
        String imageUrl = bookImagePersistenceAdapter.getImageUrl(bookId);

        // then
        assertThat(imageUrl).isEqualTo(gcsUrl);
    }
}