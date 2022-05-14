package com.mateuszroszkowski.WeLoveBooks.controller;

import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.BookRateDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.RateDTO;
import com.mateuszroszkowski.WeLoveBooks.security.CurrentUser;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import com.mateuszroszkowski.WeLoveBooks.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping()
    public List<BookDTO> getAllBooks(){
        return bookService.getAllBooksDto();
    }

    @GetMapping("/{id}")
    public BookDTO getBookById(@PathVariable Long id){
        return bookService.getBookDtoById(id);
    }

    @PostMapping
    public BookDTO createBook(@RequestBody BookDTO bookDTO){
        return bookService.createBook(bookDTO);
    }

    @PatchMapping("/{id}")
    public BookDTO updateBook(@PathVariable Long id, @RequestBody BookDTO bookDTO){
        return bookService.updateBook(id, bookDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
    }

    @PostMapping("/rate/{bookId}")
    public BookDTO rateBook(@PathVariable Long bookId, @CurrentUser UserPrincipal userPrincipal, @RequestBody RateDTO rate){
        return bookService.rateBook(bookId, userPrincipal.getId(), rate.getRate());
    }

    @PutMapping("/rate/{bookId}")
    public BookDTO updateRate(@PathVariable Long bookId, @CurrentUser UserPrincipal userPrincipal, @RequestBody RateDTO rate){
        return bookService.updateRate(bookId, userPrincipal.getId(), rate.getRate());
    }

    @GetMapping("/search")
    public List<BookDTO> searchBook(@RequestParam String q){
        return bookService.search(q);
    }

    @GetMapping("/category/{category}")
    public List<BookDTO> getBooksByCategory(@PathVariable String category){
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/getMyRate/{bookId}")
    public Integer getMyRate(@PathVariable Long bookId, @CurrentUser UserPrincipal userPrincipal){
        return bookService.getMyRate(bookId, userPrincipal.getId());
    }

    @GetMapping("/getMyFriendsRate/{bookId}")
    public float getMyFriendsRate(@PathVariable Long bookId, @CurrentUser UserPrincipal userPrincipal){
        return bookService.getMyFriendsRate(bookId, userPrincipal.getId());
    }

    @GetMapping("/getMyBookRates")
    public List<BookRateDTO> getMyBookRates(@CurrentUser UserPrincipal userPrincipal){
        return bookService.getMyBookRates(userPrincipal.getId());
    }

    @GetMapping("/getMyFriendBookRates/{friendId}")
    public List<BookRateDTO> getMyFriendBookRates(@PathVariable Long friendId){
        return bookService.getMyFriendBookRates(friendId);
    }

    @GetMapping("/getMyFriendsBookRates/{bookId}")
    public List<BookRateDTO> getMyFriendsBookRates(@CurrentUser UserPrincipal user, @PathVariable Long bookId) {
        return bookService.getMyFriendsBookRates(user.getId(), bookId);
    }
}