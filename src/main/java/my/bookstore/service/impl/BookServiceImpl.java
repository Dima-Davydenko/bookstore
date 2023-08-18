package my.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.BookDto;
import my.bookstore.dto.CreateBookRequestDto;
import my.bookstore.exception.EntityNotFoundException;
import my.bookstore.mapper.BookMapper;
import my.bookstore.model.Book;
import my.bookstore.repository.BookRepository;
import my.bookstore.service.BookService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.getBookById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id)
        );
        return bookMapper.toDto(book);
    }
}
