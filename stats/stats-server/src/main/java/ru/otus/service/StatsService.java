package ru.otus.service;

import ru.otus.dto.EndpointHitDto;
import ru.otus.dto.ViewStats;
import ru.otus.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    EndpointHit create(EndpointHitDto endpointHitDto);

    List<ViewStats> getAll(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
