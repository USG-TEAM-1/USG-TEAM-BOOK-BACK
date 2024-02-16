package com.usg.book.adapter.in.web;

import com.usg.book.adapter.in.web.dto.BookRegisterRequest;
import com.usg.book.adapter.in.web.dto.BookRegisterResponse;
import com.usg.book.adapter.in.web.dto.BookUpdateRequest;
import com.usg.book.adapter.in.web.dto.BookUpdateResponse;
import com.usg.book.adapter.in.web.dto.Result;
import com.usg.book.adapter.in.web.token.MemberEmailGetter;
import com.usg.book.application.port.in.BookDeleteCommend;
import com.usg.book.application.port.in.BookDeleteUseCase;
import com.usg.book.application.port.in.BookImageUploadUseCase;
import com.usg.book.application.port.in.BookRegisterCommend;
import com.usg.book.application.port.in.BookRegisterUseCase;
import com.usg.book.application.port.in.BookUpdateCommend;
import com.usg.book.application.port.in.BookUpdateUseCase;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookApiController {

    private final BookRegisterUseCase bookRegisterUseCase;
    private final BookImageUploadUseCase bookImageUploadUseCase;
    private final BookDeleteUseCase bookDeleteUseCase;
    private final BookUpdateUseCase bookUpdateUseCase;
    private final MemberEmailGetter memberEmailGetter;

    @Operation(summary = "책 등록 *")
    @PostMapping(value = "/api/book", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> registerBook(@ModelAttribute("BookRegisterRequest") BookRegisterRequest request,
                                               HttpServletRequest servletRequest) {

        // JWT 에서 이메일 가져오기
        // String email = memberEmailGetter.getMemberEmail(servletRequest.getHeader("Authorization"));
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


    @Operation(summary = "책 삭제 *")
    @DeleteMapping("/api/book/{bookId}")
    public ResponseEntity<Result> deleteBook(@PathVariable Long bookId,
                                             HttpServletRequest servletRequest) {

    // JWT 에서 이메일 가져오기
        //String email = memberEmailGetter.getMemberEmail(servletRequest.getHeader("Authorization"));
        BookDeleteCommend bookDeleteCommend = BookDeleteCommend.builder()
                //.email(email)
                .bookId(bookId)
                .build();

        bookDeleteUseCase.deleteBook(bookDeleteCommend);

        return ResponseEntity.ok(new Result(null, "책 삭제가 완료되었습니다."));
    }

    @Operation(summary = "책 수정 *")
    @PutMapping(value = "/api/book/{bookId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Result> updateBook(@PathVariable Long bookId,
                                         @ModelAttribute("BookUpdateRequest") BookUpdateRequest request,
                                         HttpServletRequest servletRequest) {

        // JWT 에서 이메일 가져오기
        //String email = memberEmailGetter.getMemberEmail(servletRequest.getHeader("Authorization"));
        BookUpdateCommend bookUpdateCommend = requestToUpdateCommend(request, "email", bookId);
        bookUpdateUseCase.updateBook(bookUpdateCommend);

        // 이미지 수정 로직 구현 (bookImageUpdateUseCase 사용)
        //bookImageUpdateUseCase.

        return ResponseEntity.ok(new Result(BookUpdateResponse.builder().updatedBookId(bookId).build(),"책 수정이 완료되었습니다."));
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

    private BookUpdateCommend requestToUpdateCommend(BookUpdateRequest request, String email, Long bookId) {
        return BookUpdateCommend
                .builder()
                .email(email)
                .bookId(bookId)
                .bookPostName(request.getBookPostName())
                .bookComment(request.getBookComment())
                .bookPrice(request.getBookPrice())
                //.images(request.getImages())  // 이미지 수정 로직에 따라 수정
                .build();
    }
}
