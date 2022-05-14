package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.ReviewDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.ReviewMapper;
import com.mateuszroszkowski.WeLoveBooks.model.Book;
import com.mateuszroszkowski.WeLoveBooks.model.Review;
import com.mateuszroszkowski.WeLoveBooks.model.User;
import com.mateuszroszkowski.WeLoveBooks.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReviewService {
    private final BookService bookService;
    private final UserService userService;
    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;

    public ReviewService(BookService bookService, UserService userService, ReviewMapper reviewMapper, ReviewRepository reviewRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
    }

    public ReviewDTO addReview(Long bookId, Long userId, ReviewDTO reviewDTO){
        Book book = bookService.getBookById(bookId);
        User user = userService.getUserById(userId);
        Review review = new Review();
        review.setContent(reviewDTO.getContent());
        review.setBook(book);
        review.setUser(user);

        return reviewMapper.map(reviewRepository.save(review));
    }

    public void deleteReviewById(Long userId, Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
            new RuntimeException("Cannot delete review - review doesn't exists"));
        if(review.getUser().getId().equals(userId)){
            reviewRepository.delete(review);
        }
        else{
            throw new RuntimeException("delete forbidden");
        }
    }

    public ReviewDTO updateReviewById(Long userId, ReviewDTO reviewDTO, Long reviewId){
        Review review = reviewRepository.findById(reviewId).orElseThrow(() ->
                new RuntimeException("Cannot update review - review doesn't exists"));
        if(review.getUser().getId().equals(userId)){
            if(reviewDTO.getContent()!=null){
                review.setContent(reviewDTO.getContent());
            }
            return reviewMapper.map(reviewRepository.save(review));
        }
        else{
            throw new RuntimeException("delete forbidden");
        }
    }

    public ReviewDTO getReviewById(Long reviewId){
        return reviewMapper.map(reviewRepository.findById(reviewId).orElseThrow(() ->
                new RuntimeException("Review doesn't exists")));
    }

    public Set<ReviewDTO> getReviewByBook(Long bookId){
        Book book = bookService.getBookById(bookId);
        Set<ReviewDTO> reviewDTOS = new HashSet<>();
        book.getReviews().forEach(r -> reviewDTOS.add(reviewMapper.map(r)));
        return reviewDTOS;
    }
}
