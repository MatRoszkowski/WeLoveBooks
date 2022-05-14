package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.ReviewDTO;
import com.mateuszroszkowski.WeLoveBooks.security.CurrentUser;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import com.mateuszroszkowski.WeLoveBooks.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService=reviewService;
    }

    @PostMapping("/books/{bookId}")
    public ReviewDTO addReview(@PathVariable Long bookId, @CurrentUser UserPrincipal userPrincipal, @RequestBody ReviewDTO reviewDTO){
        return reviewService.addReview(bookId, userPrincipal.getId(), reviewDTO);
    }

    @GetMapping("/books/{bookId}")
    public Set<ReviewDTO> getReviewByBook(@PathVariable Long bookId){
        return reviewService.getReviewByBook(bookId);
    }

    @GetMapping("/{reviewId}")
    public ReviewDTO getReview(@PathVariable Long reviewId){
        return reviewService.getReviewById(reviewId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, @CurrentUser UserPrincipal userPrincipal, @RequestBody ReviewDTO reviewDTO){
        reviewService.deleteReviewById(userPrincipal.getId(), reviewId);
    }

    @PatchMapping("/{reviewId}")
    public ReviewDTO updateReview(@CurrentUser UserPrincipal userPrincipal, @RequestBody ReviewDTO reviewDTO, @PathVariable Long reviewId){
        return reviewService.updateReviewById(userPrincipal.getId(), reviewDTO, reviewId);
    }

}
