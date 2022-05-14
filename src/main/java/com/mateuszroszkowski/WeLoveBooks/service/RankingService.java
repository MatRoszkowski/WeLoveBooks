package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.BookMapper;
import com.mateuszroszkowski.WeLoveBooks.repository.BookRateRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankingService {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookRateRepository bookRateRepository;

    public RankingService(BookService bookService, BookMapper bookMapper, BookRateRepository bookRateRepository) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.bookRateRepository = bookRateRepository;
    }

    public List<BookDTO> findTopHundredBooks() {
        return bookService.getAllBooksDto()
                .stream()
                .sorted(Comparator.comparing(BookDTO::getRate).reversed())
                .limit(100)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findMostOftenRated() {
        return bookRateRepository.findDistinctBooks()
                .stream()
                .sorted((o1, o2) -> o2.getRates().size() - o1.getRates().size())
                .map(book -> bookMapper.map(book))
                .limit(5)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findLastAddedBooks() {
        List<BookDTO> books = bookService.getAllBooksDto();
        return books.subList(books.size() - 5, books.size());
    }

    public List<BookDTO> findTopFiveBooks() {
        return bookService.getAllBooksDto()
                .stream()
                .sorted(Comparator.comparing(BookDTO::getRate).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
