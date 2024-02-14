package com.usg.book.adapter.in.web;

import com.usg.book.adapter.in.web.dto.BookRegisterRequest;
import com.usg.book.adapter.in.web.dto.BookRegisterResponse;
import com.usg.book.adapter.in.web.dto.GetBookResponse;
import com.usg.book.adapter.in.web.dto.Result;
import com.usg.book.adapter.in.web.token.MemberEmailGetter;
import com.usg.book.application.port.in.*;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookRegisterUseCase bookRegisterUseCase;
    private final BookImageUploadUseCase bookImageUploadUseCase;
    private final MemberEmailGetter memberEmailGetter;
    private final GetBookUseCase getBookUseCase;

    @Operation(summary = "책 등록 *")
    @PostMapping(value = "/api/book", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> registerBook(@ModelAttribute("BookRegisterRequest") BookRegisterRequest request,
                                               HttpServletRequest servletRequest) {

        // JWT 에서 이메일 가져오기
        String email = memberEmailGetter.getMemberEmail(servletRequest.getHeader("Authorization"));
        BookRegisterCommend bookRegisterCommend = requestToCommend(request, email);
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
                .bookRealPrice(request.getBookRealPrice())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .bookPostName(request.getBookPostName())
                .bookComment(request.getBookComment())
                .bookPrice(request.getBookPrice())
                .isbn(request.getIsbn())
                .build();
    }

    @Operation(summary = "책 상세 조회")
    @GetMapping("/api/book/{bookId}")
    public ResponseEntity<Result> getBook(@PathVariable(name = "bookId") Long bookId) {

        GetBookServiceResponse getBookServiceResponse = getBookUseCase.getBook(bookId);

        return ResponseEntity.ok(new Result(GetBookResponse
                .builder()
                .bookName(getBookServiceResponse.getBookName())
                .bookComment(getBookServiceResponse.getBookComment())
                .bookPostName(getBookServiceResponse.getBookPostName())
                .bookPrice(getBookServiceResponse.getBookPrice())
                .bookRealPrice(getBookServiceResponse.getBookRealPrice())
                .nickname(getBookServiceResponse.getNickname())
                .imageUrl(getBookServiceResponse.getImageUrl())
                .author(getBookServiceResponse.getAuthor())
                .publisher(getBookServiceResponse.getPublisher())
                .build(),
                "책 조회가 완료되었습니다."));
    }

}
