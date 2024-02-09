package com.usg.book.adapter.in.web.dto;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRegisterRequest {

    private Long memberId; // 어떤 사용자가 책을 등록했는지 알기 위함
    private String bookName; // 책 이름
    private String bookComment; // 책 코멘트
    private String bookPostName; // 책 등록 시 게시글 이름
    private Long bookPrice; // 책 가격
    private String isbn; // ISBN

    @Builder
    public BookRegisterRequest(Long memberId, String bookName, String bookComment, String bookPostName, Long bookPrice, String isbn) {
        this.memberId = memberId;
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookPostName = bookPostName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
    }
}
