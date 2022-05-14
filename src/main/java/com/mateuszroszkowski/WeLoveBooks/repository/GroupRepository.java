package com.mateuszroszkowski.WeLoveBooks.repository;


import com.mateuszroszkowski.WeLoveBooks.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
