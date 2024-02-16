package com.usg.book.adapter.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookEntity {

    @Id @GeneratedValue
    @Column(name = "book_id")
    private Long id;
    private String email;
    private String bookName;
    private String bookComment;
    private String bookPostName;
    private Integer bookPrice;
    private String isbn;

    // 이미지 추가 예정

    @Builder
    public BookEntity(String email, String bookName, String bookComment, String bookPostName, Integer bookPrice, String isbn) {
        this.email = email;
        this.bookName = bookName;
        this.bookComment = bookComment;
        this.bookPostName = bookPostName;
        this.bookPrice = bookPrice;
        this.isbn = isbn;
    }
}
