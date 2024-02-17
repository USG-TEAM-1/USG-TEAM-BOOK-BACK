package com.usg.book.application.service;

import org.springframework.stereotype.Service;

import com.usg.book.application.port.in.BookDeleteCommend;
import com.usg.book.application.port.in.BookDeleteUseCase;
import com.usg.book.application.port.out.BookPersistencePort;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookDeleteService implements BookDeleteUseCase{
    
    private final BookPersistencePort bookPersistencePort;

    @Override
    @Transactional
    public void deleteBook(BookDeleteCommend commend) {
       
        bookPersistencePort.deleteById(commend.getBookId());
    }
}
