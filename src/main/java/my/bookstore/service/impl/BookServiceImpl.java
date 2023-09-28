package my.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.book.BookDto;
import my.bookstore.dto.book.BookSearchParameters;
import my.bookstore.dto.book.CreateBookRequestDto;
import my.bookstore.exception.EntityNotFoundException;
import my.bookstore.mapper.BookMapper;
import my.bookstore.model.Book;
import my.bookstore.repository.book.BookRepository;
import my.bookstore.repository.book.BookSpecificationBuilder;
import my.bookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find book by id " + id));
        return bookMapper.toDto(book);
    }

    @Override
    public void delete(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDto> search(BookSearchParameters searchParameters) {
        return bookRepository.findAll(bookSpecificationBuilder.build(searchParameters))
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto update(CreateBookRequestDto requestDto, Long id) {
        Book bookToUpdate = bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't update book. Incorrect id " + id));
        bookToUpdate.setTitle(requestDto.getTitle());
        bookToUpdate.setAuthor(requestDto.getAuthor());
        bookToUpdate.setPrice(requestDto.getPrice());
        bookToUpdate.setIsbn(requestDto.getIsbn());
        bookToUpdate.setDescription(requestDto.getDescription());
        bookToUpdate.setCoverImage(requestDto.getCoverImage());
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }
}
