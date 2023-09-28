package my.bookstore.service;

import java.util.List;
import my.bookstore.dto.book.BookDto;
import my.bookstore.dto.book.BookSearchParameters;
import my.bookstore.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    void delete(Long id);

    List<BookDto> search(BookSearchParameters searchParameters);

    BookDto update(CreateBookRequestDto requestDto, Long id);
}
