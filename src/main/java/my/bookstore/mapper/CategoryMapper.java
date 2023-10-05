package my.bookstore.mapper;

import my.bookstore.config.MapperConfig;
import my.bookstore.dto.category.CategoryDto;
import my.bookstore.dto.category.CategoryRequestDto;
import my.bookstore.model.Category;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    Category toModel(CategoryRequestDto categoryRequestDto);
}
