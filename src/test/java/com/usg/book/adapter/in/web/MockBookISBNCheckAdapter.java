package com.usg.book.adapter.in.web;

import com.usg.book.application.port.out.BookISBNCheckPort;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Primary
@Component
@Profile("test")
public class MockBookISBNCheckAdapter implements BookISBNCheckPort {

    @Override
    public void bookIsbnCheck(String isbn, Integer price) {
        return;
    }
}
