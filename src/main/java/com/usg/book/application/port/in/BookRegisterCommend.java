package com.usg.book.application.port.in;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookRegisterCommend {

    // Request 가 아닌 Commend 에서 유효성 검사를 해야된다
    // https://velog.io/@msung99/Hexagonal-Architecture-%ED%97%A5%EC%82%AC%EA%B3%A0%EB%82%A0-%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98%EC%97%90%EC%84%9C-%EC%9C%A0%EC%A6%88%EC%BC%80%EC%9D%B4%EC%8A%A4UserCase-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0

    private String email; // 어떤 사용자가 책을 등록했는지 알기 위함
    private String bookName; // 책 이름
    private String bookComment; // 책 코멘트
    private String bookPostName; // 책 등록 시 게시글 이름
    private Integer bookPrice; // 책 가격
    private String isbn; // ISBN

    @Builder
    public BookRegisterCommend(String email, String bookName, String bookComment, String bookPostName, Integer bookPrice, String isbn) {
        this.email = email;
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookPostName = bookPostName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
    }
}
