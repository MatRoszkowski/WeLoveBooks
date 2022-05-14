package com.mateuszroszkowski.WeLoveBooks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorRate {
    @EmbeddedId
    private AuthorRateId id;

    @ManyToOne
    @MapsId("authorId")
    private Author author;

    @ManyToOne
    @MapsId("userId")
    private User user;

    private Integer rate;

}
