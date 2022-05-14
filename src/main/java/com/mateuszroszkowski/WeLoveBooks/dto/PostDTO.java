package com.mateuszroszkowski.WeLoveBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDTO {
    private Long id;
    private String content;
    private BookDTO book;
    private UserDTO user;

    public PostDTO(Long id, String content, BookDTO book, UserDTO user) {
        this.id = id;
        this.content = content;
        this.book = book;
        this.user = user;
    }
}
