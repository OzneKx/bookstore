package com.application.bookstore.api;

import com.application.bookstore.domain.entity.Book;
import com.application.bookstore.domain.enums.Publisher;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    final Long id;
    final String title;
    final String author;
    final int publishYear;
    final List<String> languages;
    final Publisher publisher;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publishYear = book.getPublishYear();
        this.languages = book.getLanguages();
        this.publisher = book.getPublisher();
    }
}
