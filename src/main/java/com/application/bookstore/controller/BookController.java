package com.application.bookstore.controller;

import com.application.bookstore.data.entity.Book;
import com.application.bookstore.data.mapper.BookMapper;
import com.application.bookstore.dto.BookRequest;
import com.application.bookstore.dto.BookResponse;
import com.application.bookstore.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Tag(name = "Books", description = "Manage books in the bookstore")
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    public BookController(BookService bookService, BookMapper bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @Operation(
            summary = "Create a new  book",
            description = "Creates a new book using the provided request payload."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Book created successfully",
            content = @Content(schema = @Schema(implementation = BookResponse.class))
    )
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody
                                                       @Parameter(description = "Book data to create")
                                                       BookRequest bookRequest) {
        Book createdBook = bookService.createBook(bookRequest);
        BookResponse response = bookMapper.toResponse(createdBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Get all books",
            description = "Returns the list of all books in the store."
    )
    @ApiResponse(responseCode = "204", description = "No books found")
    @GetMapping
    public ResponseEntity<List<BookResponse>> getBooks() {
        List<Book> books = bookService.getBooks();

        if (books.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(bookMapper.toResponseList(books));
    }

    @Operation(summary = "Get book by ID")
    @ApiResponse(responseCode = "200", description = "Book found")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@Parameter(description = "ID of the book to retrieve")
                                                        @PathVariable Long id) {
        Book book = bookService.getBooksById(id);
        return ResponseEntity.ok(bookMapper.toResponse(book));
    }

    @Operation(summary = "Update book by ID")
    @ApiResponse(responseCode = "200", description = "Book updated successfully")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@Parameter(description = "ID of the book to update")
                                                       @PathVariable Long id,
                                                   @Valid @RequestBody @Parameter(description = "Updated book data")
                                                   BookRequest bookRequest) {
        Book updatedBook = bookService.updateBook(id, bookRequest);
        return ResponseEntity.ok(bookMapper.toResponse(updatedBook));
    }

    @Operation(
            summary = "Delete book by ID",
            description = "Deletes a book by its ID. Requires ADMIN role",
            security = { @SecurityRequirement(name = "bearerAuth")}
    )
    @ApiResponse(responseCode = "204", description = "Book deleted successfully")
    @ApiResponse(responseCode = "404", description = "Book not found")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@Parameter(description = "ID of the book to delete") @PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

