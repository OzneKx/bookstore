package com.application.bookstore.controller;

import com.application.bookstore.data.entity.Book;
import com.application.bookstore.data.enums.Publisher;
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

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest) {
        Book book = convertToEntity(bookRequest);
        Book savedBook = bookService.createBook(book);

        BookResponse response = new BookResponse(savedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks() {
        List<Book> books = bookService.getBooks();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<BookResponse> response = books.stream()
                .map(BookResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        Book book = bookService.getBooksById(id);
        return ResponseEntity.ok(new BookResponse(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        Book book = convertToEntity(bookRequest);
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(new BookResponse(updatedBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    private Book convertToEntity(BookRequest bookRequest) {
        Book book = new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setPublishYear(bookRequest.getPublishYear());
        book.setLanguages(bookRequest.getLanguages());
        book.setPublisher(Publisher.valueOf(bookRequest.getPublisher().toUpperCase()));
        return book;
    }
}
