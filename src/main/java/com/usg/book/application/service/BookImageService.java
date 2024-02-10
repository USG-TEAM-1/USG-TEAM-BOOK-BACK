package com.usg.book.application.service;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.application.port.in.BookImageUploadUseCase;
import com.usg.book.application.port.out.BookImageGcsPort;
import com.usg.book.application.port.out.BookImagePersistencePort;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookImageService implements BookImageUploadUseCase {

    private final BookImagePersistencePort bookImagePersistencePort;
    private final BookImageGcsPort bookImageGcsPort;
    private final BookPersistencePort bookPersistencePort;

    @Override
    @Transactional
    public void saveImages(List<MultipartFile> images, Long bookId) {

        if (images.size() > 10) {
            throw new IllegalArgumentException("Image Capacity Exceeded");
        }

        BookEntity findBookEntity = bookPersistencePort.findById(bookId);
        Long findBookEntityId = findBookEntity.getId();

        for (MultipartFile imageFile : images) {
            if (!imageFile.isEmpty()) {

                String originalFilename = imageFile.getOriginalFilename();
                String storeFilename = createStoreFilename(originalFilename);

                Image image = Image.builder()
                        .image(imageFile)
                        .bookId(findBookEntityId)
                        .storeFilename(storeFilename)
                        .build();

                String gcsUrl = bookImageGcsPort.uploadImage(image);

                image.setGcsUrl(gcsUrl);

                bookImagePersistencePort.saveImage(image, findBookEntity);
            }
        }
    }

    private String createStoreFilename(String originalFilename) {
        // 서버에 저장하는 관리명
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1); // 확장자 .png가져오기
    }
}
