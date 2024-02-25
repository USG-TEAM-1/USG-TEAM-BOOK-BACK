package com.usg.book.adapter.out.persistence;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.BookRepository;
import com.usg.book.adapter.out.persistence.entity.ImageEntity;
import com.usg.book.adapter.out.persistence.entity.ImageRepository;
import com.usg.book.application.port.out.BookImagePersistencePort;
import com.usg.book.domain.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookImagePersistenceAdapter implements BookImagePersistencePort {

    private final ImageRepository imageRepository;
    private final BookRepository bookRepository;

    @Override
    public Long saveImage(Image image, Long bookId) {

        BookEntity findBookEntity = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("Book Not Exist")
        );

        ImageEntity imageEntity = ImageEntity
                .builder()
                .uploadFilename(image.getOriginalFilename())
                .storeFilename(image.getStoreFilename())
                .gcsUrl(image.getGcsUrl())
                .bookEntity(findBookEntity)
                .build();

        ImageEntity savedImageEntity = imageRepository.save(imageEntity);

        return savedImageEntity.getId();
    }

    @Override
    public List<String> getImageUrls(Long bookId) {
        List<ImageEntity> imageList = imageRepository.findByBookId(bookId);

        List<String> imageUrls = new ArrayList<>();
        for (ImageEntity imageEntity : imageList) {
            imageUrls.add(imageEntity.getGcsUrl());
        }

        return imageUrls;
    }

    @Override
    public List<ImageEntity> getImagesByBookId(Long bookId) {
        return imageRepository.findImagesByBookId(bookId);
    }

    @Override
    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }
}

