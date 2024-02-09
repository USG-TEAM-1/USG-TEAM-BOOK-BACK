package com.usg.book.adapter.in.web;

import com.usg.book.adapter.in.web.dto.BookRegisterRequest;
import com.usg.book.adapter.in.web.dto.BookRegisterResponse;
import com.usg.book.adapter.in.web.dto.Result;
import com.usg.book.application.port.in.BookRegisterCommend;
import com.usg.book.application.port.in.BookRegisterUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookRegisterUseCase bookRegisterUseCase;

    @PostMapping("/api/book")
    public ResponseEntity<Result> registerBook(@RequestBody BookRegisterRequest request) {

        BookRegisterCommend bookRegisterCommend = requestToCommend(request);
        Long savedBookId = bookRegisterUseCase.registerBook(bookRegisterCommend);

        return ResponseEntity.ok(new Result(BookRegisterResponse
                .builder()
                .savedBookId(savedBookId)
                .build(),
                "책 등록이 완료되었습니다."));
    }

    private BookRegisterCommend requestToCommend(BookRegisterRequest request) {
        return BookRegisterCommend
                .builder()
                .memberId(request.getMemberId())
                .bookName(request.getBookName())
                .bookComment(request.getBookComment())
                .bookPostName(request.getBookPostName())
                .bookPrice(request.getBookPrice())
                .isbn(request.getIsbn())
                .build();
    }
}
