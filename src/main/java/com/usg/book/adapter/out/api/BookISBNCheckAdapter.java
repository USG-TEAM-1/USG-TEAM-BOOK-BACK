package com.usg.book.adapter.out.api;

import com.usg.book.application.port.out.BookISBNCheckPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookISBNCheckAdapter implements BookISBNCheckPort {

    @Override
    public void bookIsbnCheck(String isbn) {
        return;
    }
}
