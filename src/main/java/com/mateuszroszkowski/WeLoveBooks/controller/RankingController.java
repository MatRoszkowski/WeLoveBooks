package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.service.RankingService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/rankings")
@CrossOrigin(origins = "http://localhost:3000")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping()
    public List<BookDTO> getTopHundredBooks() {
        return rankingService.findTopHundredBooks();
    }

    @GetMapping("/oftenRated")
    public List<BookDTO> getMostOftenRated() {
        return rankingService.findMostOftenRated();
    }

    @GetMapping("/lastAdded")
    public List<BookDTO> getLastAddedBooks() {
        return rankingService.findLastAddedBooks();
    }

    @GetMapping("/top5")
    public List<BookDTO> getTopFiveBooks() {
        return rankingService.findTopFiveBooks();
    }
}
