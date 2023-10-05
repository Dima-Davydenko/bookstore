package my.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import my.bookstore.dto.book.BookDtoWithoutCategoryIds;
import my.bookstore.dto.category.CategoryDto;
import my.bookstore.dto.category.CategoryRequestDto;
import my.bookstore.mapper.BookMapper;
import my.bookstore.mapper.CategoryMapper;
import my.bookstore.model.Category;
import my.bookstore.repository.book.BookRepository;
import my.bookstore.repository.category.CategoryRepository;
import my.bookstore.service.CategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final CategoryMapper categoryMapper;
    private final BookMapper bookMapper;

    @Override
    public List<CategoryDto> findAll(Pageable pageable) {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find category by id " + id));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryRequestDto) {
        Category category = categoryMapper.toModel(categoryRequestDto);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Override
    public CategoryDto update(CategoryRequestDto requestDto, Long id) {
        Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Can't find category by id " + id));
        categoryToUpdate.setName(requestDto.getName());
        categoryToUpdate.setDescription(requestDto.getDescription());
        return categoryMapper.toDto(categoryRepository.save(categoryToUpdate));
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long categoryId) {
        return bookRepository.findAllByCategoryId(categoryId).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();
    }
}
