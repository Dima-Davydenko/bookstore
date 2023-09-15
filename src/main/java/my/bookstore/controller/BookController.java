package my.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.BookDto;
import my.bookstore.dto.BookSearchParameters;
import my.bookstore.dto.CreateBookRequestDto;
import my.bookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books", description = "Get a list of all available books")
    public List<BookDto> findAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @PostMapping
    @Operation(summary = "Create a new Book", description = "Saves a new book in database")
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find book by it's ID",
            description = "Find book by it's ID, if one exists")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/search")
    @Operation(summary = "Find book by it's parameter",
            description = "Find book by it's author or title")
    public List<BookDto> search(BookSearchParameters searchParameters) {
        return bookService.search(searchParameters);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book", description = "Delete book from database by ID")
    public void delete(@PathVariable Long id) {
        bookService.delete(id);
    }
}
