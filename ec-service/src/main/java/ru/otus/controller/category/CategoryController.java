package ru.otus.controller.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.CategoryDto;
import ru.otus.service.category.CategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                    @RequestParam(defaultValue = "10") @Positive Integer size) {
        log.info("Get request for getting all categories.");
        return categoryService.getAll(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto get(@PathVariable Long catId) {
        log.info("Get request for getting category with id={}", catId);
        return categoryService.get(catId);
    }
}
