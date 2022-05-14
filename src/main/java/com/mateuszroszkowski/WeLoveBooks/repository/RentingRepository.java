package com.mateuszroszkowski.WeLoveBooks.repository;


import com.mateuszroszkowski.WeLoveBooks.model.Renting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentingRepository extends JpaRepository<Renting, Long> {
}
