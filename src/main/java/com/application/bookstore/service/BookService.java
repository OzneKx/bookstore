package com.application.bookstore.service;

import com.application.bookstore.api.BookRequest;
import com.application.bookstore.api.BookResponse;
import com.application.bookstore.domain.entity.Book;
import com.application.bookstore.domain.enums.Publisher;
import com.application.bookstore.domain.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookResponse createBook(BookRequest bookRequest) {
        Book book = new Book(bookRequest);
        return new BookResponse(bookRepository.save(book));
    }

    public List<BookResponse> getBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookResponse::new).toList();
    }

    public BookResponse getBooksById(Long id) {
        Book existentBook = checkExistentBook(id);

        return new BookResponse(existentBook);
    }

    public BookResponse updateBook(Long id, BookRequest bookRequest) {
        Book existentBook = checkExistentBook(id);

        existentBook.setTitle(bookRequest.getTitle());
        existentBook.setAuthor(bookRequest.getAuthor());
        existentBook.setPublishYear(bookRequest.getPublishYear());
        existentBook.setLanguages(bookRequest.getLanguages());
        existentBook.setPublisher(Publisher.valueOf(bookRequest.getPublisher().toUpperCase()));

        return new BookResponse(bookRepository.save(existentBook));
    }

    public void deleteBook(Long id) {
        Book existentBook = checkExistentBook(id);
        bookRepository.delete(existentBook);
    }

    private Book checkExistentBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
