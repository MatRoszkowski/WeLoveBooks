package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.PostDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.PostMapper;
import com.mateuszroszkowski.WeLoveBooks.model.Book;
import com.mateuszroszkowski.WeLoveBooks.model.Post;
import com.mateuszroszkowski.WeLoveBooks.model.User;
import com.mateuszroszkowski.WeLoveBooks.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class PostService {
    private final BookService bookService;
    private final UserService userService;
    private final PostMapper postMapper;
    private final PostRepository postRepository;

    public PostService(BookService bookService, UserService userService, PostMapper postMapper, PostRepository postRepository) {
        this.bookService = bookService;
        this.userService = userService;
        this.postMapper = postMapper;
        this.postRepository = postRepository;
    }

    public PostDTO addPost(Long bookId, Long userId, PostDTO postDTO) {
        Book book = bookService.getBookById(bookId);
        User user = userService.getUserById(userId);
        Post post = new Post();
        post.setContent(postDTO.getContent());
        post.setBook(book);
        post.setUser(user);

        return postMapper.map(postRepository.save(post));
    }

    public void deletePostById(Long userId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new RuntimeException("Cannot delete post - post doesn't exists"));
        if (post.getUser().getId().equals(userId)) {
            postRepository.delete(post);
        } else {
            throw new RuntimeException("delete forbidden");
        }
    }

    public PostDTO updatePostById(Long userId, PostDTO postDTO, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new RuntimeException("Cannot update post - post doesn't exists"));
        if (post.getUser().getId().equals(userId)) {
            if (postDTO.getContent() != null) {
                post.setContent(postDTO.getContent());
            }
            return postMapper.map(postRepository.save(post));
        } else {
            throw new RuntimeException("delete forbidden");
        }
    }

    public PostDTO getPostById(Long postId) {
        return postMapper.map(postRepository.findById(postId).orElseThrow(() ->
                new RuntimeException("Post doesn't exists")));
    }

    public Set<PostDTO> getPostByBook(Long bookId) {
        Book book = bookService.getBookById(bookId);
        Set<PostDTO> postDTOS = new HashSet<>();
        book.getPosts().forEach(p -> postDTOS.add(postMapper.map(p)));
        return postDTOS;
    }
}
