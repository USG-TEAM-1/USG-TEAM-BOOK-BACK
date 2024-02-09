package com.usg.book.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private Long memberId;
    private String bookName;
    private String bookComment;
    private String bookPostName;
    private Integer bookPrice;
    private String isbn;

    // 이미지 추가 예정

    @Builder
    public BookEntity(Long memberId, String bookName, String bookComment, String bookPostName, Integer bookPrice, String isbn) {
        this.memberId = memberId;
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookPostName = bookPostName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
    }
}
