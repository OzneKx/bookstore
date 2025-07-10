package com.application.bookstore.api;

import com.application.bookstore.domain.enums.Publisher;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    @NotBlank(message = "Title is required")
    @Size(max = 200)
    String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100)
    String author;

    @Min(1000)
    @Max(2025)
    int publishYear;

    @Size(min = 1)
    List<String> languages;

    @NotNull
    Publisher publisher;
}
