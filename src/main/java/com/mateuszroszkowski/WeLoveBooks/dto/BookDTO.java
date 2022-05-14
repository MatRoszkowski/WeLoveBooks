package com.mateuszroszkowski.WeLoveBooks.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDate publicationDate;
    private String publishingHouse;
    private String image;
    private float rate;
    private List<String> genres;
    private List<AuthorDTO> authors;
    private int numberOfRates;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Long> authorsIds = new ArrayList<>();

    public BookDTO(Long id, String title, String description, LocalDate publicationDate, String publishingHouse, String image, float rate, List<String> genres, List<AuthorDTO> authors, int numberOfRates) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.publicationDate = publicationDate;
        this.publishingHouse = publishingHouse;
        this.image = image;
        this.rate = rate;
        this.genres = genres;
        this.authors = authors;
        this.numberOfRates = numberOfRates;
    }
}