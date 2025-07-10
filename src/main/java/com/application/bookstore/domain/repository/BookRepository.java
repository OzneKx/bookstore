package com.application.bookstore.domain.repository;

import com.application.bookstore.domain.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
