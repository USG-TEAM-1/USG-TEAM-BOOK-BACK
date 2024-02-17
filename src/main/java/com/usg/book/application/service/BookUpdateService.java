package com.usg.book.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usg.book.adapter.out.persistence.entity.BookEntity;
import com.usg.book.application.port.in.BookUpdateCommend;
import com.usg.book.application.port.in.BookUpdateUseCase;
import com.usg.book.application.port.out.BookPersistencePort;
import com.usg.book.domain.Book;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookUpdateService implements BookUpdateUseCase{
    private final BookPersistencePort bookPersistencePort;

    @Override
    @Transactional
    public Long updateBook(BookUpdateCommend bookUpdateCommend) {
        // BookPersistencePort를 사용하여 책을 찾아 업데이트
        Optional<BookEntity> optionalExistingBook = Optional.ofNullable(bookPersistencePort.findById(bookUpdateCommend.getBookId()));
        
        // Book이 존재하는지 확인하고, 그렇지 않으면 예외        
        BookEntity existingBook = optionalExistingBook.orElseThrow(() ->
                new RuntimeException("Book not found with id: " + bookUpdateCommend.getBookId()));

       // 업데이트할 정보를 Commend 객체로부터 추출  
       String updatedBookComment = bookUpdateCommend.getBookComment();
       String updatedBookPostName = bookUpdateCommend.getBookPostName();
       Integer updatedBookPrice = bookUpdateCommend.getBookPrice();

       // 추출한 정보를 사용하여 책 업데이트
       existingBook.setBookComment(updatedBookComment);
       existingBook.setBookPostName(updatedBookPostName);
       existingBook.setBookPrice(updatedBookPrice);

       // 업데이트된 책을 저장하고, 업데이트된 책의 ID를 반환
       BookEntity updatedBook = bookPersistencePort.save(existingBook);
       return updatedBook.getId();
    }
}
