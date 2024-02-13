package com.usg.book.adapter.out.api;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.usg.book.application.port.out.BookImageGcsPort;
import com.usg.book.domain.Image;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
public class ImageGcsAdapter implements BookImageGcsPort {

    private final String bucketName;
    private final String projectId;
    private final Storage storage;

    public ImageGcsAdapter(@Value("${spring.cloud.gcp.storage.bucket}") String bucketName,
                           @Value("${spring.cloud.gcp.storage.project-id}") String projectId,
                           Storage storage) {

        this.bucketName = bucketName;
        this.projectId = projectId;
        this.storage = storage;
    }

    @Override
    public String uploadImage(Image image) {

        try {
            MultipartFile multipartFile = image.getImage();
            String ext = multipartFile.getContentType();
            String storeFilename = image.getStoreFilename();

            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, storeFilename)
                    .setContentType(ext)
                    .build();

            Blob blob = storage.create(blobInfo, multipartFile.getInputStream());

            return "https://storage.googleapis.com/" + bucketName + "/" + storeFilename;
        } catch (Exception e) {
            throw new IllegalArgumentException("Image Error");
        }
    }
}
