package com.application.bookstore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Payload used to create or update a book")
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(max = 200)
    @JsonProperty("title")
    @Schema(description = "Title of the book", example = "Clean Code")
    String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100)
    @JsonProperty("author")
    @Schema(description = "Author of the book", example = "Robert C. Martin")
    String author;

    @Min(value = 1000, message = "Publish year must be at least 1000")
    @Max(value = 2025, message = "Publish year cannot be greater than 2025")
    @JsonProperty("publishYear")
    @Schema(description = "Year the book was published", example = "2008")
    int publishYear;

    @NotEmpty(message = "At least one language is required")
    @JsonProperty("languages")
    @Schema(description = "Languages available for the book", example = "[\"EN\", \"PT\"]")
    List<String> languages;

    @NotBlank(message = "Publisher is required")
    @JsonProperty("publisher")
    @Schema(description = "Publisher of the book", example = "PENGUIN_RANDOM_HOUSE")
    String publisher;
}
