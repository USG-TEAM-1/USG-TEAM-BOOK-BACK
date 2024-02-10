package com.usg.book.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {

    private String email; // 어떤 사용자가 책을 등록했는지 알기 위함
    private String bookName; // 책 이름
    private String bookComment; // 책 코멘트
    private String bookPostName; // 책 등록 시 게시글 이름
    private Integer bookPrice; // 책 가격
    private String isbn; // ISBN

    @Builder
    public Book(String email, String bookName, String bookComment, String bookPostName, Integer bookPrice, String isbn) {
        this.email = email;
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookPostName = bookPostName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
    }
}
