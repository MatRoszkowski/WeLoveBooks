package com.mateuszroszkowski.WeLoveBooks.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRateDTO {
    private BookDTO book;
    private UserDTO user;
    private Integer rate;

    public BookRateDTO(BookDTO book, UserDTO user, Integer rate) {
        this.book = book;
        this.user = user;
        this.rate = rate;
    }
}
