package com.mateuszroszkowski.WeLoveBooks.repository;


import com.mateuszroszkowski.WeLoveBooks.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
