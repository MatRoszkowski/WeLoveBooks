package com.mateuszroszkowski.WeLoveBooks.dto.mapper;

import com.mateuszroszkowski.WeLoveBooks.dto.AuthorDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.model.Book;
import com.mateuszroszkowski.WeLoveBooks.model.BookRate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper implements Mapper<Book, BookDTO> {

    private final AuthorMapper authorMapper;

    public BookMapper(AuthorMapper authorMapper) {
        this.authorMapper = authorMapper;
    }

    @Override
    public BookDTO map(Book book){
        int rates = 0;
        for (BookRate r : book.getRates()) {
            rates += r.getRate();
        }

        float avgRate = 0;
        int numberOfRates = 0;
        if(!book.getRates().isEmpty()) {
            numberOfRates = book.getRates().size();
            avgRate = Math.round(((float) rates / numberOfRates) * 10f) / 10f;
        }

        List<AuthorDTO> authors = new ArrayList<>();
        book.getAuthors().forEach(a -> authors.add(authorMapper.map(a)));

        List<String> genres = new ArrayList<>();
        book.getGenres().forEach(g -> genres.add(g.getName()));

        return new BookDTO(book.getId(), book.getTitle(), book.getDescription(), book.getPublicationDate(), book.getPublishingHouse(), book.getImage(), avgRate, genres, authors, numberOfRates);
    }
}
