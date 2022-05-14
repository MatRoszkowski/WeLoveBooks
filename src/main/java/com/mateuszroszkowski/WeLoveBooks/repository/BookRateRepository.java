package com.mateuszroszkowski.WeLoveBooks.repository;

import com.mateuszroszkowski.WeLoveBooks.model.Book;
import com.mateuszroszkowski.WeLoveBooks.model.BookRate;
import com.mateuszroszkowski.WeLoveBooks.model.BookRateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRateRepository extends JpaRepository<BookRate, BookRateId> {
    @Query("select distinct book from BookRate")
    List<Book> findDistinctBooks();

    List<BookRate> countAllByBook(Book book);
}
