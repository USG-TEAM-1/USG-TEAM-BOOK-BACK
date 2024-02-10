package com.usg.book.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    private MultipartFile image;
    private Long bookId;
    private String gcsUrl;
    private String storeFilename;

    @Builder
    public Image(MultipartFile image, Long bookId, String gcsUrl, String storeFilename) {
        this.image = image;
        this.bookId = bookId;
        this.gcsUrl = gcsUrl;
        this.storeFilename = storeFilename;
    }

    public void setGcsUrl(String gcsUrl) {
        this.gcsUrl = gcsUrl;
    }
}
