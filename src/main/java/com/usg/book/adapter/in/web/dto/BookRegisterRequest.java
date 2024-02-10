package com.usg.book.adapter.in.web.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class BookRegisterRequest {

    private String bookName; // 책 이름
    private String bookComment; // 책 코멘트
    private String bookPostName; // 책 등록 시 게시글 이름
    private Integer bookPrice; // 책 가격
    private String isbn; // ISBN
    private List<MultipartFile> images = new ArrayList<>(); // 이미지

    @Builder
    public BookRegisterRequest(String bookName, String bookComment, String bookPostName, Integer bookPrice, String isbn, List<MultipartFile> images) {
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookPostName = bookPostName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
        this.images = images;
    }
}
