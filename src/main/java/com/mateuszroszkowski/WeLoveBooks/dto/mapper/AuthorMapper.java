package com.mateuszroszkowski.WeLoveBooks.dto.mapper;

import com.mateuszroszkowski.WeLoveBooks.dto.AuthorDTO;
import com.mateuszroszkowski.WeLoveBooks.model.Author;
import com.mateuszroszkowski.WeLoveBooks.model.AuthorRate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AuthorMapper implements Mapper<Author, AuthorDTO> {
    @Override
    public AuthorDTO map(Author author) {
        int rates = 0;
        for (AuthorRate rate : author.getRates()) {
            rates += rate.getRate();
        }

        float avgRate = 0;
        int numberOfRates = 0;
        if (!author.getRates().isEmpty()) {
            numberOfRates = author.getRates().size();
            avgRate = (float) rates / numberOfRates;
        }

        int authorAge = 0;
        LocalDate start = author.getDateOfBirth();
        LocalDate end = (author.getDateOfDeath() != null) ? author.getDateOfDeath() : LocalDate.now();

        if (start != null) {
            Period period = Period.between(start, end);
            authorAge = period.getYears();
        }

        return new AuthorDTO(author.getId(), author.getFirstName(), author.getLastName(), authorAge, author.getDateOfBirth(), author.getDateOfDeath(), author.getPhoto(), author.getInformation(), avgRate, numberOfRates);
    }
}
