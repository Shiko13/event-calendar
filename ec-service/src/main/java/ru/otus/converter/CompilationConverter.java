package ru.otus.converter;

import lombok.experimental.UtilityClass;
import ru.otus.dto.CompilationDto;
import ru.otus.dto.EventShortDto;
import ru.otus.dto.NewCompilationDto;
import ru.otus.model.Compilation;
import ru.otus.model.Event;

import java.util.List;

@UtilityClass
public class CompilationConverter {
    public static CompilationDto toDto(Compilation compilation, List<EventShortDto> events) {
        return new CompilationDto(compilation.getId(), compilation.getPinned(),
                compilation.getTitle(), events);
    }

    public Compilation fromDto(NewCompilationDto newCompilationDto, List<Event> events) {
        return new Compilation(null, newCompilationDto.getPinned(),
                newCompilationDto.getTitle(), events);
    }
}
