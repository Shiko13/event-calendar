package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.dto.EndpointHitDto;
import ru.otus.dto.ViewStats;
import ru.otus.model.EndpointHit;
import ru.otus.service.StatsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StatsController {

    private final StatsServiceImpl statsService;


    @GetMapping("/stats")
    public List<ViewStats> getAll(@RequestParam LocalDateTime start, @RequestParam LocalDateTime end,
                                  @RequestParam(required = false) List<String> uris,
                                  @RequestParam(defaultValue = "false") Boolean unique) {
        log.info("Get /stats with start={}, end={}, uris={}, unique={}", start, end, uris, unique);
        System.out.println("TESTING: /stats with start={}, end={}, uris={}, unique={} "
                + start + " " + end + " " + uris + " " + unique);
        return statsService.getAll(start, end, uris, unique);
    }

    @PostMapping("/hit")
    @ResponseStatus(HttpStatus.CREATED)
    public EndpointHit create(@RequestBody EndpointHitDto endpointHitDto) {
        log.info("Post /hit with endpointHitDto={}", endpointHitDto);
        return statsService.create(endpointHitDto);
    }
}
