package ru.otus.converter;

import lombok.experimental.UtilityClass;
import ru.otus.dto.ParticipationRequestDto;
import ru.otus.model.ParticipationRequest;

@UtilityClass
public class RequestConverter {

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return new ParticipationRequestDto(request.getId(), request.getCreated(),
                request.getEvent().getId(), request.getRequester().getId(),
                 request.getStatus());
    }
}
