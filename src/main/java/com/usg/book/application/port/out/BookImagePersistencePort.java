package com.usg.book.application.port.out;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.domain.Image;

public interface BookImagePersistencePort {

    Long saveImage(Image image, BookEntity book);
    String getImageUrl(Long bookId);
}
