package com.usg.book.application.service;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.adapter.out.persistence.entity.ImageEntity;
import com.usg.book.application.port.in.BookImageDeleteUseCase;
import com.usg.book.application.port.in.BookImageUpdateUseCase;
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
public class BookImageService implements BookImageUploadUseCase, BookImageDeleteUseCase, BookImageUpdateUseCase{

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

    
    @Override
    @Transactional
    public void deleteImages(Long bookId) {
        List<ImageEntity> imageEntities = bookImagePersistencePort.getImagesByBookId(bookId);

        for (ImageEntity imageEntity : imageEntities) {
            if (imageEntity != null) {

                String gcsImageUrl = imageEntity.getGcsUrl();
                if (gcsImageUrl != null) {
                    bookImageGcsPort.deleteImage(gcsImageUrl);
                }

                bookImagePersistencePort.deleteImage(imageEntity);

            }
        }
    }

    @Override
    public void updateImages(List<MultipartFile> images, Long bookId) {

        if (images.size() > 10) {
            throw new IllegalArgumentException("Image Capacity Exceeded");
        }
        
        //책과 연관된 이미지를 삭제
        //deleteImages(bookId);
        List<ImageEntity> imageEntities = bookImagePersistencePort.getImagesByBookId(bookId);

        for (ImageEntity imageEntity : imageEntities) {
            if (imageEntity != null) {

                String gcsImageUrl = imageEntity.getGcsUrl();
                if (gcsImageUrl != null) {
                    bookImageGcsPort.deleteImage(gcsImageUrl);
                }

                bookImagePersistencePort.deleteImage(imageEntity);

            }
        }

        
        BookEntity findBookEntity = bookPersistencePort.findById(bookId);

        // 새로운 이미지를 업로드하고 저장
        for (MultipartFile imageFile : images) {
            if (!imageFile.isEmpty()) {
                String originalFilename = imageFile.getOriginalFilename();
                String storeFilename = createStoreFilename(originalFilename);

                Image image = Image.builder()
                        .image(imageFile)
                        .bookId(bookId)
                        .storeFilename(storeFilename)
                        .build();

                String gcsUrl = bookImageGcsPort.uploadImage(image);
                image.setGcsUrl(gcsUrl);

                bookImagePersistencePort.saveImage(image, findBookEntity);
            }
        }
    }
}
