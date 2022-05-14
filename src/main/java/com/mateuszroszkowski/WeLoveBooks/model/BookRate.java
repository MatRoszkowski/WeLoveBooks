package com.mateuszroszkowski.WeLoveBooks.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class BookRate {
    @EmbeddedId
    private BookRateId id;

    @ManyToOne
    @MapsId("bookId")
    private Book book;

    @ManyToOne
    @MapsId("userId")
    private User user;

    private Integer rate;
}
