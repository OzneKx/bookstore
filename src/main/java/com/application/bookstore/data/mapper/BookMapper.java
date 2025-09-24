package com.application.bookstore.data.mapper;

import com.application.bookstore.data.entity.Book;
import com.application.bookstore.dto.BookRequest;
import com.application.bookstore.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "author", target = "author"),
            @Mapping(source = "publishYear", target = "publishYear"),
            @Mapping(source = "languages", target = "languages"),
            @Mapping(source = "publisher", target = "publisher")
    })
    Book toEntity(BookRequest request);

    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "title", target = "title"),
            @Mapping(source = "author", target = "author"),
            @Mapping(source = "publishYear", target = "publishYear"),
            @Mapping(source = "languages", target = "languages"),
            @Mapping(source = "publisher", target = "publisher")
    })
    BookResponse toResponse(Book book);

    List<BookResponse> toResponseList(List<Book> books);

    void updateBookFromRequest(BookRequest request, @MappingTarget Book book);
}