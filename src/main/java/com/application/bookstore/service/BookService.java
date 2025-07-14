package com.application.bookstore.service;

import com.application.bookstore.data.entity.Book;
import com.application.bookstore.data.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> getBooks() {
        return bookRepository.findAll();
    }

    public Book getBooksById(Long id) {
        return checkExistentBook(id);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Book existentBook = checkExistentBook(id);

        existentBook.setTitle(updatedBook.getTitle());
        existentBook.setAuthor(updatedBook.getAuthor());
        existentBook.setPublishYear(updatedBook.getPublishYear());
        existentBook.setLanguages(updatedBook.getLanguages());
        existentBook.setPublisher(updatedBook.getPublisher());

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
