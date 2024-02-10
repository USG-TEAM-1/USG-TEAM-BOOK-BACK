package com.usg.book.application.service;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.application.port.out.BookImageGcsPort;
import com.usg.book.application.port.out.BookImagePersistencePort;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookImageServiceTest {

    @InjectMocks
    private BookImageService bookImageService;
    @Mock
    private BookImagePersistencePort bookImagePersistencePort;
    @Mock
    private BookImageGcsPort bookImageGcsPort;
    @Mock
    private BookPersistencePort bookPersistencePort;

    @Test
    @DisplayName("이미지 저장 서비스 테스트")
    void saveImagesTest() {
        // given
        Long bookId = 1L;
        List<MultipartFile> images = new ArrayList<>();
        MockMultipartFile image = new MockMultipartFile("images", "image1.jpg", "image/jpeg", new byte[10]);
        images.add(image);

        // stub
        when(bookPersistencePort.findById(bookId)).thenAnswer(invocation -> {
            BookEntity bookEntity = BookEntity.builder().build();
            setPrivateField(bookEntity, "id", 1L);  // Reflection으로 ID 값을 설정
            return bookEntity;
        });
        when(bookImageGcsPort.uploadImage(any(Image.class))).thenReturn("gcsUrl");
        when(bookImagePersistencePort.saveImage(any(Image.class), any(BookEntity.class))).thenReturn(1L);

        // when
        bookImageService.saveImages(images, bookId);
    }

    private void setPrivateField(Object object, String fieldName, Object value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }
}