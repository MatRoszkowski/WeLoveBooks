package com.mateuszroszkowski.WeLoveBooks.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Integer age;
    private LocalDate dateOfBirth;
    private LocalDate dateOfDeath;
    private String photo;
    private String information;
    private float rate;
    private int numberOfRates;

    public AuthorDTO(Long id, String firstName, String lastName, Integer age, LocalDate dateOfBirth, LocalDate dateOfDeath, String photo, String information, float rate, int numberOfRates) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.dateOfDeath = dateOfDeath;
        this.photo = photo;
        this.information = information;
        this.rate = rate;
        this.numberOfRates = numberOfRates;
    }
}
