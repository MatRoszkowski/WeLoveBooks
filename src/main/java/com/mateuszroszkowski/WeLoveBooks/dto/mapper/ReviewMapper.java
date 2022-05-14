package com.mateuszroszkowski.WeLoveBooks.dto.mapper;

import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.ReviewDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper implements Mapper<Review, ReviewDTO> {
    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    public ReviewMapper(BookMapper bookMapper, UserMapper userMapper) {
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
    }

    @Override
    public ReviewDTO map(Review review) {
        UserDTO userDTO = userMapper.map(review.getUser());
        BookDTO bookDTO = bookMapper.map(review.getBook());
        return new ReviewDTO(review.getId(), bookDTO, userDTO, review.getContent());
    }
}
