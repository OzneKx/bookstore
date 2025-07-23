package com.application.bookstore.service;

import com.application.bookstore.data.entity.Book;
import com.application.bookstore.data.mapper.BookMapper;
import com.application.bookstore.data.repository.BookRepository;

import com.application.bookstore.dto.BookRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    public Book createBook(BookRequest request) {
        Book book = bookMapper.toEntity(request);
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBooksById(Long id) {
        return checkExistentBook(id);
    }

    public Book updateBook(Long id, BookRequest request) {
        Book existentBook = checkExistentBook(id);
        bookMapper.updateBookFromRequest(request, existentBook);
        return bookRepository.save(existentBook);
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
