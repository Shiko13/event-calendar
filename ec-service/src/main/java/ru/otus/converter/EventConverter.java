package ru.otus.converter;

import lombok.experimental.UtilityClass;
import ru.otus.dto.EventFullDto;
import ru.otus.dto.EventShortDto;
import ru.otus.dto.NewEventDto;
import ru.otus.model.User;
import ru.otus.model.State;
import ru.otus.model.Category;
import ru.otus.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class EventConverter {


    public static Event fromDto(User initiator, Category category, NewEventDto newEventDto) {
        return new Event(
                null,
                newEventDto.getAnnotation(),
                category,
                newEventDto.getDescription(),
                newEventDto.getEventDate(),
                newEventDto.getLocation(),
                initiator,
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(),
                newEventDto.getTitle(),
                LocalDateTime.now(),
                null,
                State.PENDING
        );
    }

    public static List<EventShortDto> toEventShortDtoList(List<Event> events,
                                                          Map<Long, Integer> confirmedRequests,
                                                          Map<Long, Long> views) {
        return events.stream()
                .map(e -> toEventShortDto(e, confirmedRequests, views))
                .collect(Collectors.toList());
    }

    public static EventShortDto toEventShortDto(Event event,
                                                Map<Long, Integer> confirmedRequests,
                                                Map<Long, Long> views) {
        return new EventShortDto(
                event.getId(),
                event.getAnnotation(),
                CategoryConverter.toDto(event.getCategory()),
                Optional.ofNullable(confirmedRequests.get(event.getId())).orElse(0),
                event.getEventDate(),
                UserConverter.toShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                Optional.ofNullable(views.get(event.getId())).orElse(0L)
        );
    }

    public static EventFullDto toFullDto(Event event,
                                         Map<Long, Integer> confirmedRequests,
                                         Map<Long, Long> views) {
        return new EventFullDto(
                event.getAnnotation(),
                event.getCategory(),
                Optional.ofNullable(confirmedRequests.get(event.getId())).orElse(0),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                event.getInitiator(),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.getRequestModeration(),
                event.getState(),
                event.getTitle(),
                Optional.ofNullable(views.get(event.getId())).orElse(0L),
                event.getCreatedOn()
        );
    }

    public static List<EventFullDto> toFullDtoList(List<Event> events,
                                                        Map<Long, Integer> confirmedRequests,
                                                        Map<Long, Long> views) {
        return events.stream()
                .map(e -> toFullDto(e, confirmedRequests, views))
                .collect(Collectors.toList());
    }
}
