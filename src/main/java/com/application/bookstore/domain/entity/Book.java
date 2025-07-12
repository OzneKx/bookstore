package com.application.bookstore.domain.entity;

import com.application.bookstore.api.BookRequest;

import com.application.bookstore.domain.enums.Publisher;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GenerationType;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;
    String author;
    int publishYear;

    @ElementCollection
    @CollectionTable(name = "book_languages", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "language")
    List<String> languages;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    Publisher publisher;

    public Book(BookRequest request) {
        this.title = request.getTitle();
        this.author = request.getAuthor();
        this.publishYear = request.getPublishYear();
        this.languages = request.getLanguages();
        this.publisher = Publisher.valueOf(request.getPublisher());
    }
}
