package ru.otus.service.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.model.UpdateCompilationRequest;
import ru.otus.client.StatsClient;
import ru.otus.converter.CompilationConverter;
import ru.otus.converter.EventConverter;
import ru.otus.dto.CompilationDto;
import ru.otus.dto.EventShortDto;
import ru.otus.dto.NewCompilationDto;
import ru.otus.exception.NotFoundParameterException;
import ru.otus.repository.CompilationRepository;
import ru.otus.model.Status;
import ru.otus.exception.BadRequestException;
import ru.otus.model.Compilation;
import ru.otus.model.Event;
import ru.otus.repository.EventRepository;
import ru.otus.repository.RequestRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final StatsClient statsClient;
    private final RequestRepository requestRepository;
    private final EventRepository eventRepository;


    @Override
    public List<CompilationDto> getAll(Boolean pinned, Integer from, Integer size) {
        log.info("Getting all compilations from repository");
        Pageable pageable = PageRequest.of(from / size, size);
        List<Compilation> compilations;

        if (pinned == null) {
            compilations = compilationRepository.findAll();
        } else {
            compilations = compilationRepository.findByPinnedOrderById(pinned, pageable);
        }

        return compilations.stream()
                .map(c -> CompilationConverter.toDto(c, toEventShortDtoMapper(c.getEvents())))
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDto getById(Long compId) {
        log.info("Getting compilation by id from repository");
        Compilation compilation = getCompilationFromRepository(compId);
        List<Event> events = compilation.getEvents();

        return CompilationConverter.toDto(compilation, toEventShortDtoMapper(events));
    }

    @Override
    @Transactional
    public CompilationDto create(NewCompilationDto newCompilationDto) {
        List<Event> events = eventRepository.findAllById(newCompilationDto.getEvents());
        Compilation compilation = CompilationConverter.fromDto(newCompilationDto, events);
        Compilation newCompilation = compilationRepository.save(compilation);
        log.info("New compilation with id={} created successfully.", newCompilation.getId());

        return CompilationConverter.toDto(newCompilation, toEventShortDtoMapper(events));
    }

    @Override
    @Transactional
    public void delete(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Override
    @Transactional
    public CompilationDto update(Long compId, UpdateCompilationRequest request) {
        Compilation compilation =
                compilationRepository.findById(compId).orElseThrow(() -> new NotFoundParameterException("Wrong compilation"));
        List<Event> events = null;

        if (request.getEvents() != null) {
            events = eventRepository.findAllById(request.getEvents());
            compilation.setEvents(events);
        }
        if (request.getPinned() != null) {
            compilation.setPinned(request.getPinned());
        }
        if (request.getTitle() != null) {
            compilation.setTitle(request.getTitle());
        }

        return CompilationConverter.toDto(compilationRepository.save(compilation), getEventShortDtoList(events));
    }

    private List<EventShortDto> toEventShortDtoMapper(List<Event> events) {
        List<EventShortDto> eventsDto = new ArrayList<>();
        if (events != null) {
            List<Long> eventIds = getEventIds(events);
            Map<Long, Long> views = statsClient.getHits(eventIds);
            Map<Long, Integer> eventsRequests = new HashMap<>();
            List<Integer> requestList = requestRepository.countAllByStatusAndEvent_IdsIn(
                    Status.CONFIRMED, eventIds);
            for (int i = 0; i < eventIds.size(); i++) {
                eventsRequests.put(eventIds.get(i), requestList.get(i));
            }
            eventsDto = EventConverter.toEventShortDtoList(events, eventsRequests, views);
        }
        return eventsDto;
    }

    private List<Long> getEventIds(Iterable<Event> events) {
        List<Long> result = new ArrayList<>();
        events.forEach(e -> result.add(e.getId()));
        return result;
    }

    private Compilation getCompilationFromRepository(Long compilationId) {
        return compilationRepository.findById(compilationId)
                .orElseThrow(() -> {
                    throw new BadRequestException("Compilation", compilationId, LocalDateTime.now());
                });
    }

    private List<EventShortDto> getEventShortDtoList(List<Event> events) {
        if (events != null) {
            List<Long> eventIds = getEventIds(events);
            Map<Long, Long> views = statsClient.getHits(eventIds);
            Map<Long, Integer> eventsRequests = getEventRequests(eventIds);

            return EventConverter.toEventShortDtoList(events, eventsRequests, views);
        }
        return Collections.emptyList();
    }

    private Map<Long, Integer> getEventRequests(List<Long> eventIds) {
        Map<Long, Integer> eventsRequests = new HashMap<>();
        List<Integer> requestList = requestRepository.countAllByStatusAndEvent_IdsIn(
                Status.CONFIRMED, eventIds);
        for (int i = 0; i < eventIds.size(); i++) {
            eventsRequests.put(eventIds.get(i), requestList.get(i));
        }

        return eventsRequests;
    }
}
