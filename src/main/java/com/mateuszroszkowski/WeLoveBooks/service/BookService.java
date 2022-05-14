package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.BookRateDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.UserDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.BookMapper;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.UserMapper;
import com.mateuszroszkowski.WeLoveBooks.model.*;
import com.mateuszroszkowski.WeLoveBooks.repository.BookRateRepository;
import com.mateuszroszkowski.WeLoveBooks.repository.BookRepository;
import com.mateuszroszkowski.WeLoveBooks.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService{
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookRateRepository bookRateRepository;
    private final UserRepository userRepository;
    private final AuthorService authorService;
    private final UserMapper userMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, BookRateRepository bookRateRepository,
                       UserRepository userRepository, AuthorService authorService, UserMapper userMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.bookRateRepository = bookRateRepository;
        this.userRepository = userRepository;
        this.authorService = authorService;
        this.userMapper = userMapper;
    }

    public BookDTO createBook(BookDTO bookDTO){
        Book book = new Book();

        book.setTitle(bookDTO.getTitle());
        book.setImage(bookDTO.getImage());
        book.setDescription(bookDTO.getDescription());
        book.setPublicationDate(bookDTO.getPublicationDate());
        book.setPublishingHouse(bookDTO.getPublishingHouse());

        addAuthorsToBook(book, bookDTO.getAuthorsIds());

        return bookMapper.map(bookRepository.save(book));
    }

    public List<BookDTO> getAllBooksDto(){
        List<BookDTO> books = new ArrayList<>();
        bookRepository.findAll().forEach(b -> books.add(bookMapper.map(b)));

        return books;
    }

    public BookDTO getBookDtoById(Long id){
        return bookMapper.map(getBookById(id));
    }

    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = getBookById(id);

        if(bookDTO.getTitle() != null) {
            book.setTitle(bookDTO.getTitle());
        }
        if(bookDTO.getTitle() != null) {
            book.setImage(bookDTO.getImage());
        }
        if(bookDTO.getDescription() != null) {
            book.setDescription(bookDTO.getDescription());
        }
        if(bookDTO.getPublicationDate() != null) {
            book.setPublicationDate(bookDTO.getPublicationDate());
        }
        if(bookDTO.getPublishingHouse() != null) {
            book.setPublishingHouse(bookDTO.getPublishingHouse());
        }
        if(bookDTO.getAuthorsIds().size()>0) {
            addAuthorsToBook(book, bookDTO.getAuthorsIds());
        }

        return bookMapper.map(bookRepository.save(book));
    }

    public BookDTO rateBook(Long bookId, Long userId, Integer rate){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        BookRate bookRate;

        Optional<BookRate> bookRateOptional = bookRateRepository
                .findById(new BookRateId(book.getId(), user.getId()));
        if (bookRateOptional.isPresent()) {
            bookRate = bookRateOptional.get();
        }
        else {
            bookRate = new BookRate();
            bookRate.setId(new BookRateId(book.getId(), user.getId()));
            bookRate.setBook(book);
            bookRate.setUser(user);
        }

        bookRate.setRate(rate);
        bookRateRepository.save(bookRate);
        return bookMapper.map(book);
    }

    public BookDTO updateRate(Long bookId, Long userId, Integer rate){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        BookRate bookRate = bookRateRepository.findById(new BookRateId(bookId, userId)).
                orElseThrow(() -> new RuntimeException("That user didn't rate that book"));
        if(user.getId().equals(bookRate.getUser().getId())){
            bookRate.setRate(rate);
            bookRateRepository.save(bookRate);
            return bookMapper.map(bookRepository.findById(bookId).
                    orElseThrow(() -> new RuntimeException("Couldn't update book rate - book doesn't exists")));
        }
        else {
            throw new RuntimeException("update rate forbidden");
        }
    }

    public List<BookDTO> search(String query) {
        List<BookDTO> bookDTOS = new ArrayList<>();
        List<Book> foundBooks = bookRepository.findAll().stream().filter(b -> b.getTitle().toLowerCase().contains(query.toLowerCase())).collect(Collectors.toList());
        foundBooks.forEach(b -> bookDTOS.add(bookMapper.map(b)));

        return bookDTOS;
    }

    public List<BookDTO> getBooksByCategory(String category){
        List<Book> books = bookRepository.findAll().stream()
                .filter(b -> b.getGenres().stream().anyMatch(g -> g.getName().toLowerCase().equals(category.toLowerCase())))
                .collect(Collectors.toList());

        List<BookDTO> bookDTOS = books.stream().map(b -> bookMapper.map(b))
                .collect(Collectors.toList());

        return bookDTOS;
    }

    private void addAuthorsToBook(Book book, List<Long> authorsIds) {
        Set<Author> authors = new HashSet<>();
        authorsIds.forEach(id -> {
            authors.add(authorService.getAuthorById(id));
        });
        book.setAuthors(authors);
    }

    public Integer getMyRate(Long bookId, Long userId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        BookRate bookRate;
        Integer myRate;

        Optional<BookRate> bookRateOptional = bookRateRepository
                .findById(new BookRateId(book.getId(), user.getId()));
        if (bookRateOptional.isPresent()) {
            bookRate = bookRateOptional.get();
            myRate = bookRate.getRate();
        }
        else {
            myRate = 0;
        }
        return myRate;
    }

    public float getMyFriendsRate(Long bookId, Long userId){
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        float myFriendsRate;
        int sumRates = 0;
        int numberOfRates = 0;
        Set<User> friends = user.getFriends();

        if(!friends.isEmpty()) {
            for (User u : friends) {
                Integer rateTMP = getMyRate(book.getId(), u.getId());
                sumRates += rateTMP;
                if(rateTMP != 0) numberOfRates += 1;
            }
            myFriendsRate = (float) sumRates / numberOfRates;
        }
        else myFriendsRate = 0;

        return myFriendsRate;
    }

    public List<BookRateDTO> getMyBookRates(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<BookRateDTO> bookRateDTOs = new ArrayList<>();

        Set<BookRate> bookRates = user.getBookRates();
        if (!bookRates.isEmpty()){
            bookRates.forEach(b -> bookRateDTOs.add(new BookRateDTO(bookMapper.map(b.getBook()), new UserDTO(user.getId(), user.getUsername(), user.getPassword(),
                    user.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet())), b.getRate())));
        }

        return bookRateDTOs;
    }

    public List<BookRateDTO> getMyFriendBookRates(Long friendId){
        User friend = userRepository.findById(friendId).orElseThrow(() -> new RuntimeException("User not found"));

        List<BookRateDTO> bookRateDTOs = new ArrayList<>();

        friend.getBookRates().forEach(rates ->
            bookRateDTOs.add(
                    new BookRateDTO(bookMapper.map(rates.getBook()), userMapper.map(friend), rates.getRate())));

        return bookRateDTOs;
    }

    public List<BookRateDTO> getMyFriendsBookRates(Long userId, Long bookId){
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Book book = getBookById(bookId);
        List<BookRateDTO> bookRateDTOs = new ArrayList<>();
        user.getFriends().forEach(friend -> {
            if(friend.getBookRates().stream().anyMatch(bookRate -> bookRate.getBook().getId().equals(bookId))){
                bookRateDTOs.add(
                        new BookRateDTO(bookMapper.map(book), userMapper.map(friend),
                                friend.getBookRates()
                                        .stream()
                                        .filter(rate -> rate.getBook().getId().equals(bookId))
                                        .findFirst()
                                        .orElseThrow(() -> new RuntimeException("Friend hasn't rated that book"))
                                        .getRate()));
            }
        });

        return bookRateDTOs;
    }
}