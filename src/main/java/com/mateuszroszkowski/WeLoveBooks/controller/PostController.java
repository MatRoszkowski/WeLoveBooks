package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.PostDTO;
import com.mateuszroszkowski.WeLoveBooks.security.CurrentUser;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import com.mateuszroszkowski.WeLoveBooks.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/books/{bookId}")
    public PostDTO addPost(@PathVariable Long bookId, @CurrentUser UserPrincipal userPrincipal, @RequestBody PostDTO postDTO){
        return postService.addPost(bookId, userPrincipal.getId(), postDTO);
    }

    @GetMapping("/books/{bookId}")
    public Set<PostDTO> getPostByBook(@PathVariable Long bookId){
        return postService.getPostByBook(bookId);
    }

    @GetMapping("/{postId}")
    public PostDTO getPost(@PathVariable Long postId){
        return postService.getPostById(postId);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId, @CurrentUser UserPrincipal userPrincipal, @RequestBody PostDTO postDTO){
        postService.deletePostById(userPrincipal.getId(), postId);
    }

    @PatchMapping("/{postId}")
    public PostDTO updatePost(@CurrentUser UserPrincipal userPrincipal, @RequestBody PostDTO postDTO, @PathVariable Long postId){
        return postService.updatePostById(userPrincipal.getId(), postDTO, postId);
    }
}
