package ru.otus.converter;

import lombok.experimental.UtilityClass;
import ru.otus.dto.CategoryDto;
import ru.otus.dto.NewCategoryDto;
import ru.otus.model.Category;

@UtilityClass
public class CategoryConverter {

    public static CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public Category fromDto(NewCategoryDto newCategoryDto) {
        return new Category(null, newCategoryDto.getName());
    }
}
