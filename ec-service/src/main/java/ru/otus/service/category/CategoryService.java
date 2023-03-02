package ru.otus.service.category;

import ru.otus.dto.CategoryDto;
import ru.otus.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll(Integer from, Integer size);

    CategoryDto get(Long catId);

    CategoryDto create(NewCategoryDto categoryDto);

    CategoryDto update(Long id, CategoryDto categoryDto);

    void delete(Long catId);
}
