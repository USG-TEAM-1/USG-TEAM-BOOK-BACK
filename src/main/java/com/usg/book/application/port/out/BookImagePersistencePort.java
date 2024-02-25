package com.usg.book.application.port.out;

import com.usg.book.adapter.out.persistence.entity.ImageEntity;
import com.usg.book.domain.Image;

import java.util.List;

public interface BookImagePersistencePort {

    Long saveImage(Image image, Long bookId);
    List<String> getImageUrls(Long bookId);
    List<ImageEntity> getImagesByBookId(Long bookId); 
    void deleteImage(Long imageId);
}
