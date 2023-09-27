package my.bookstore.mapper;

import my.bookstore.config.MapperConfig;
import my.bookstore.dto.book.BookDto;
import my.bookstore.dto.book.CreateBookRequestDto;
import my.bookstore.model.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);
}
