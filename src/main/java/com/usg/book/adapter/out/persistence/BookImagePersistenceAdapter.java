package com.usg.book.adapter.out.persistence;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.ImageEntity;
import com.usg.book.adapter.out.persistence.entity.ImageRepository;
import com.usg.book.application.port.out.BookImagePersistencePort;
import com.usg.book.domain.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookImagePersistenceAdapter implements BookImagePersistencePort {

    private final ImageRepository imageRepository;

    @Override
    public Long saveImage(Image image, BookEntity book) {

        MultipartFile multipartFile = image.getImage();
        String originalFilename = multipartFile.getOriginalFilename();

        ImageEntity imageEntity = ImageEntity
                .builder()
                .uploadFilename(originalFilename)
                .storeFilename(image.getStoreFilename())
                .gcsUrl(image.getGcsUrl())
                .bookEntity(book)
                .build();

        ImageEntity savedImageEntity = imageRepository.save(imageEntity);

        return savedImageEntity.getId();
    }
}
