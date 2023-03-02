package ru.otus.service.compilation;

import ru.otus.model.UpdateCompilationRequest;
import ru.otus.dto.CompilationDto;
import ru.otus.dto.NewCompilationDto;

import java.util.List;

public interface CompilationService {
    List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size);

    CompilationDto getById(Long compId);

    CompilationDto create(NewCompilationDto newCompilationDto);

    void delete(Long compId);

    CompilationDto update(Long compId, UpdateCompilationRequest request);
}
