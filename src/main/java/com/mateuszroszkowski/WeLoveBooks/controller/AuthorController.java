package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.AuthorDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.security.CurrentUser;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import com.mateuszroszkowski.WeLoveBooks.service.AuthorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/authors")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping()
    public Set<AuthorDTO> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDTO getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorDtoById(id);
    }

    @PostMapping()
    public AuthorDTO createAuthor(@RequestBody AuthorDTO authorDTO) {
        return authorService.createAuthor(authorDTO);
    }

    @PatchMapping("/{id}")
    public AuthorDTO updateAuthor(@RequestBody AuthorDTO authorDTO, @PathVariable Long id) {
        return authorService.updateAuthor(id, authorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthorById(id);
    }

    @GetMapping("/books/{authorId}")
    public List<BookDTO> getBooksByAuthor(@PathVariable Long authorId) {
        return authorService.getBooksByAuthor(authorId);
    }

    @GetMapping("/search")
    public List<AuthorDTO> searchAuthors(@RequestParam String q) {
        return authorService.search(q);
    }

    @PostMapping("/{authorId}/rate")
    public AuthorDTO rateAuthor(@PathVariable Long authorId, @RequestBody Integer rate,
                                @CurrentUser UserPrincipal userPrincipal) {
        return authorService.rateAuthor(authorId, rate, userPrincipal);
    }
}
