package com.mateuszroszkowski.WeLoveBooks.service;

import com.mateuszroszkowski.WeLoveBooks.dto.AuthorDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.BookDTO;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.AuthorMapper;
import com.mateuszroszkowski.WeLoveBooks.dto.mapper.BookMapper;
import com.mateuszroszkowski.WeLoveBooks.model.Author;
import com.mateuszroszkowski.WeLoveBooks.model.AuthorRate;
import com.mateuszroszkowski.WeLoveBooks.model.AuthorRateId;
import com.mateuszroszkowski.WeLoveBooks.model.User;
import com.mateuszroszkowski.WeLoveBooks.repository.AuthorRateRepository;
import com.mateuszroszkowski.WeLoveBooks.repository.AuthorRepository;
import com.mateuszroszkowski.WeLoveBooks.repository.UserRepository;
import com.mateuszroszkowski.WeLoveBooks.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;
    private final UserRepository userRepository;
    private final AuthorRateRepository authorRateRepository;

    public AuthorService(AuthorRepository authorRepository, AuthorMapper authorMapper, BookMapper bookMapper,
                         UserRepository userRepository, AuthorRateRepository authorRateRepository) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
        this.bookMapper = bookMapper;
        this.userRepository = userRepository;
        this.authorRateRepository = authorRateRepository;
    }

    public Set<AuthorDTO> getAllAuthors(){
        Set<AuthorDTO> authorDTOS = new HashSet<>();
        authorRepository.findAll().forEach(a -> authorDTOS.add(authorMapper.map(a)));

        return authorDTOS;
    }

    public AuthorDTO getAuthorDtoById(Long id){
        return authorMapper.map(authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found")));
    }

    public Author getAuthorById(Long id){
        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public void deleteAuthorById(Long id){
        Author author = authorRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Can't delete author. Author not found"));
        author.getBooks()
                .forEach(book -> book.getAuthors().remove(author));
        authorRepository.delete(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO){
        Author author = new Author();
        author.setFirstName(authorDTO.getFirstName());
        author.setLastName(authorDTO.getLastName());
        author.setDateOfBirth(authorDTO.getDateOfBirth());
        author.setDateOfDeath(authorDTO.getDateOfDeath());
        author.setPhoto(authorDTO.getPhoto());
        author.setInformation(authorDTO.getInformation());
        return authorMapper.map(authorRepository.save(author));
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO){
        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException("Can't update author. Author not found"));
        if(authorDTO.getFirstName()!=null){
            author.setFirstName(authorDTO.getFirstName());
        }
        if(authorDTO.getLastName()!=null){
            author.setLastName(authorDTO.getLastName());
        }
        if(authorDTO.getDateOfBirth()!=null){
            author.setDateOfBirth(authorDTO.getDateOfBirth());
        }
        if(authorDTO.getDateOfDeath()!=null){
            author.setDateOfDeath(authorDTO.getDateOfDeath());
        }
        if(authorDTO.getPhoto()!=null){
            author.setPhoto(authorDTO.getPhoto());
        }
        return authorMapper.map(authorRepository.save(author));
    }

    public List<BookDTO> getBooksByAuthor(Long authorId){
        Author author = authorRepository.findById(authorId).orElseThrow(() ->
                new RuntimeException("Couldn't get books by author. Author not exists"));
        List<BookDTO> books = new ArrayList<>();
        author.getBooks().forEach(b -> books.add(bookMapper.map(b)));

        return books;
    }

    public List<AuthorDTO> search(String query){
        List<AuthorDTO> authorsDTO = new ArrayList<>();
        List<Author> authors = authorRepository.findAll().stream().filter(a ->
                        (a.getFirstName() + " " + a.getLastName()).toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        authors.forEach(a -> authorsDTO.add(authorMapper.map(a)));

        return authorsDTO;
    }

    public AuthorDTO rateAuthor(Long authorId, Integer rate, UserPrincipal userPrincipal) {
        Author author = authorRepository
                .findById(authorId)
                .orElseThrow(() -> new RuntimeException("Can't rate author. Author not found"));
        User user = userRepository
                .findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("Can't rate author. User not found"));

        AuthorRate authorRate = new AuthorRate();
        authorRate.setId(new AuthorRateId(author.getId(), user.getId()));
        authorRate.setAuthor(author);
        authorRate.setUser(user);
        authorRate.setRate(rate);

        authorRateRepository.save(authorRate);
        return authorMapper.map(author);
    }
}
