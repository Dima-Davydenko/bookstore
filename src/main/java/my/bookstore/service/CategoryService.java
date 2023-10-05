package my.bookstore.service;

import java.util.List;
import my.bookstore.dto.book.BookDtoWithoutCategoryIds;
import my.bookstore.dto.category.CategoryDto;
import my.bookstore.dto.category.CategoryRequestDto;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    List<CategoryDto> findAll(Pageable pageable);

    CategoryDto getById(Long id);

    CategoryDto save(CategoryRequestDto categoryRequestDto);

    CategoryDto update(CategoryRequestDto categoryDto, Long id);

    void deleteById(Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId);
}
