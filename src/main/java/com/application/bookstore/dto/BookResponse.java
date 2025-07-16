package com.application.bookstore.dto;

import com.application.bookstore.data.enums.Publisher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private int publishYear;
    private List<String> languages;
    private Publisher publisher;
}