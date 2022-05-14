package com.mateuszroszkowski.WeLoveBooks.repository;

import com.mateuszroszkowski.WeLoveBooks.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
