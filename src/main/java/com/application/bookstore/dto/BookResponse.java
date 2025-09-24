package com.application.bookstore.dto;

import com.application.bookstore.data.enums.Publisher;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Response object returned for book operations")
public class BookResponse {
    @Schema(description = "Unique identifier of the book", example = "1")
    private Long id;

    @Schema(description = "Title of the book", example = "Clean Code")
    private String title;

    @Schema(description = "Author of the book", example = "Robert C. Martin")
    private String author;

    @Schema(description = "Year of publication", example = "2008")
    private int publishYear;

    @Schema(description = "Languages available for this book", example = "[\"EN\", \"PT\"]")
    private List<String> languages;

    @Schema(description = "Publisher of the book", example = "PENGUIN")
    private Publisher publisher;
}