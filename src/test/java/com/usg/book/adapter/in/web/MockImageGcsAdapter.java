package com.usg.book.adapter.in.web;

import com.usg.book.application.port.out.BookImageGcsPort;
import com.usg.book.domain.Image;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Primary
@Component
@Profile("test")
public class MockImageGcsAdapter implements BookImageGcsPort {

    @Override
    public String uploadImage(Image image) {
        return "testUrl";
    }
}
