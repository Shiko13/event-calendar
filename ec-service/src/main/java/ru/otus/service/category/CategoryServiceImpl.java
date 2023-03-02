package ru.otus.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.converter.CategoryConverter;
import ru.otus.dto.CategoryDto;
import ru.otus.dto.NewCategoryDto;
import ru.otus.exception.BadRequestException;
import ru.otus.exception.CategoryIsNotEmptyException;
import ru.otus.exception.ConflictException;
import ru.otus.exception.InvalidIdException;
import ru.otus.model.Category;
import ru.otus.repository.CategoryRepository;
import ru.otus.repository.EventRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CategoryDto> getAll(Integer from, Integer size) {
        log.info("Getting all categories from repository");

        return categoryRepository.findAll(PageRequest.of(from / size, size)).stream()
                .map(CategoryConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto get(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> {
                    throw new InvalidIdException("Category", catId, LocalDateTime.now());
                });
        log.info("Getting category with id={} from repository.", category.getId());

        return CategoryConverter.toDto(category);
    }

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto categoryDto) {
        Category category = CategoryConverter.fromDto(categoryDto);

        if (categoryDto.getName() != null) {
            if (categoryRepository.existsByName(categoryDto.getName())) {
                throw new ConflictException("Category name exists", LocalDateTime.now());
            }
            category.setName(categoryDto.getName());
        }

        Category savedCategory = categoryRepository.save(category);
        log.info("New category with id={} added successfully.", savedCategory.getId());

        return CategoryConverter.toDto(savedCategory);
    }

    @Override
    public CategoryDto update(Long id, CategoryDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    throw new BadRequestException("Category", id, LocalDateTime.now());
                });

        if (categoryDto.getName() != null) {
            if (categoryRepository.existsByName(categoryDto.getName())) {
                throw new ConflictException("Category name exists", LocalDateTime.now());
            }
            category.setName(categoryDto.getName());
        }

        Category updatedCategory = categoryRepository.save(category);
        log.info("Category with id={} updated successfully.", updatedCategory.getId());

        return CategoryConverter.toDto(updatedCategory);
    }

    @Override
    @Transactional
    public void delete(Long catId) {
        if (!eventRepository.findAllByCategory_Id(catId).isEmpty()) {
            throw new CategoryIsNotEmptyException(LocalDateTime.now());
        }
        categoryRepository.deleteById(catId);
        log.info("Category with if={} deleted successfully.", catId);
    }
}
