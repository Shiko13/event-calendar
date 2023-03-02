package ru.otus.converter;

import lombok.experimental.UtilityClass;
import ru.otus.dto.LocationDto;
import ru.otus.model.Location;

@UtilityClass
public class LocationConverter {
    public static Location fromDto(LocationDto dto) {
        return new Location(
                dto.getLat(),
                dto.getLon()
        );
    }
}
