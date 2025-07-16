package com.application.bookstore.controller;

import com.application.bookstore.data.entity.Book;
import com.application.bookstore.data.mapper.BookMapper;
import com.application.bookstore.dto.BookRequest;
import com.application.bookstore.dto.BookResponse;
import com.application.bookstore.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest) {
        Book createdBook = bookService.createBook(bookRequest);
        BookResponse response = bookMapper.toResponse(createdBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks() {
        List<Book> books = bookService.getBooks();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<BookResponse> response = books.stream()
                .map(bookMapper::toResponse)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        Book book = bookService.getBooksById(id);
        return ResponseEntity.ok(bookMapper.toResponse(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        Book updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(bookMapper.toResponse(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

