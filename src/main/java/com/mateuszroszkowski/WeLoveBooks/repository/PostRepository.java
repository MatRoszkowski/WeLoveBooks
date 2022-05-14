package com.mateuszroszkowski.WeLoveBooks.repository;


import com.mateuszroszkowski.WeLoveBooks.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
