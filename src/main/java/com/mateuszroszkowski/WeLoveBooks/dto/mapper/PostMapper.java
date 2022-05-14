package com.mateuszroszkowski.WeLoveBooks.dto.mapper;

import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.PostDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper implements Mapper<Post, PostDTO> {
    private final BookMapper bookMapper;
    private final UserMapper userMapper;

    public PostMapper(BookMapper bookMapper, UserMapper userMapper) {
        this.bookMapper = bookMapper;
        this.userMapper = userMapper;
    }

    @Override
    public PostDTO map(Post post) {
        UserDTO userDTO = userMapper.map(post.getUser());
        BookDTO bookDTO = bookMapper.map(post.getBook());
        return new PostDTO(post.getId(), post.getContent(), bookDTO, userDTO);
    }
}
