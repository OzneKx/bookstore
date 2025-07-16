package com.application.bookstore.data.mapper;

import com.application.bookstore.data.entity.Book;
import com.application.bookstore.data.enums.Publisher;
import com.application.bookstore.dto.BookRequest;
import com.application.bookstore.dto.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRequest request);
    BookResponse toResponse(Book book);
    void updateBookFromRequest(BookRequest request, @MappingTarget Book book);

    default Publisher map(String publisher) {
        if (publisher == null) {
            return null;
        }
        return Publisher.valueOf(publisher);
    }
}