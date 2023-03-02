package ru.otus.converter;

import lombok.experimental.UtilityClass;
import ru.otus.dto.EndpointHitDto;
import ru.otus.model.App;
import ru.otus.model.EndpointHit;

@UtilityClass
public class EndpointHitConverter {

    public static EndpointHit toEndpointHit(EndpointHitDto endpointHitDto, App app) {
        return new EndpointHit(null, app, endpointHitDto.getUri(),
                endpointHitDto.getIp(), endpointHitDto.getTimestamp());
    }
}
