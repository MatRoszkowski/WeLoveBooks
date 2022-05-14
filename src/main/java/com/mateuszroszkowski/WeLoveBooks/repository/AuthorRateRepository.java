package com.mateuszroszkowski.WeLoveBooks.repository;

import com.mateuszroszkowski.WeLoveBooks.model.AuthorRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRateRepository extends JpaRepository<AuthorRate, Long> {
}
