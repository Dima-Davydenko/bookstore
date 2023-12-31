package my.bookstore.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import my.bookstore.config.MapperConfig;
import my.bookstore.dto.book.BookDto;
import my.bookstore.dto.book.BookDtoWithoutCategoryIds;
import my.bookstore.dto.book.CreateBookRequestDto;
import my.bookstore.model.Book;
import my.bookstore.model.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book toModel(CreateBookRequestDto requestDto);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        Set<Long> categoryIds = book.getCategories().stream()
                .map(Category::getId)
                .collect(Collectors.toSet());
        bookDto.setCategoryIds(categoryIds);
    }
}
