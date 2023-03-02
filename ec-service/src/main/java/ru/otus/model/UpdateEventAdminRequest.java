package ru.otus.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.dto.LocationDto;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UpdateEventAdminRequest {
    private String annotation;
    private Long category;
    private String description;
    private LocalDateTime eventDate;
    private LocationDto location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    private StateAction stateAction;
    private String title;
}
