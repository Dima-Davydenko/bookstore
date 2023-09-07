package my.bookstore.service;

import java.util.List;
import my.bookstore.dto.BookDto;
import my.bookstore.dto.BookSearchParameters;
import my.bookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll();

    BookDto getBookById(Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);
}
