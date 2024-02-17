package com.usg.book.application.port.in;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetBookServiceResponse {

    private String bookName;
    private String bookComment;
    private String bookPostName;
    private Integer bookPrice;
    private Integer bookRealPrice;
    private String nickname;
    private String imageUrl;
    private String author;
    private String publisher;

    @Builder
    public GetBookServiceResponse(String bookName, String bookComment, String bookPostName, Integer bookPrice, Integer bookRealPrice, String nickname, String imageUrl, String author, String publisher) {
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookPostName = bookPostName;
        this.bookPrice = bookPrice;
        this.bookRealPrice = bookRealPrice;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.author = author;
        this.publisher = publisher;
    }
}
