package com.usg.book.adapter.in.web;

import com.usg.book.adapter.in.web.dto.BookRegisterRequest;
import com.usg.book.adapter.in.web.dto.BookRegisterResponse;
import com.usg.book.adapter.in.web.dto.Result;
import com.usg.book.adapter.in.web.token.MemberEmailGetter;
import com.usg.book.application.port.in.BookImageUploadUseCase;
import com.usg.book.application.port.in.BookRegisterCommend;
import com.usg.book.application.port.in.BookRegisterUseCase;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookRegisterUseCase bookRegisterUseCase;
    private final BookImageUploadUseCase bookImageUploadUseCase;
    private final MemberEmailGetter memberEmailGetter;

    @Operation(summary = "책 등록 *")
    @PostMapping(value = "/api/book", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> registerBook(@ModelAttribute("BookRegisterRequest") BookRegisterRequest request,
                                               HttpServletRequest servletRequest) {

        // JWT 에서 이메일 가져오기
//        String email = memberEmailGetter.getMemberEmail(servletRequest.getHeader("Authorization"));
        BookRegisterCommend bookRegisterCommend = requestToCommend(request, "email");
        Long savedBookId = bookRegisterUseCase.registerBook(bookRegisterCommend);

        // 책 저장과 이미지 저장 트랜잭션 분리
        bookImageUploadUseCase.saveImages(request.getImages(), savedBookId);

        return ResponseEntity.ok(new Result(BookRegisterResponse
                .builder()
                .savedBookId(savedBookId)
                .build(),
                "책 등록이 완료되었습니다."));
    }

    private BookRegisterCommend requestToCommend(BookRegisterRequest request, String email) {
        return BookRegisterCommend
                .builder()
                .email(email)
                .bookName(request.getBookName())
                .bookComment(request.getBookComment())
                .bookPostName(request.getBookPostName())
                .bookPrice(request.getBookPrice())
                .isbn(request.getIsbn())
                .build();
    }
}
