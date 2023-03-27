package com.example.board.repository;

import com.example.board.domain.Author;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AuthorMapper {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    void save(Author author);
    Optional<Author> findByEmail(String email);
}
