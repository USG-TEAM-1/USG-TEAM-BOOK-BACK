package com.usg.book.adapter.in.web;

import com.usg.book.domain.Image;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class MockImageGcsAdapter {

    @Primary
    public String uploadImageMock(Image image) {
        return "testUrl";
    }
}
