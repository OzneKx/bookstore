package com.application.bookstore.domain.repository;

import com.application.bookstore.domain.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Publisher findByName(String name);
}
