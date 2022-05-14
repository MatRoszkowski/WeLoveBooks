package com.mateuszroszkowski.WeLoveBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private BookDTO book;
    private UserDTO user;
    private String content;

    public ReviewDTO(Long id, BookDTO book, UserDTO user,String content){
        this.id=id;
        this.book=book;
        this.user=user;
        this.content=content;
    }
}
