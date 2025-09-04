package ru.lomov.flashbackend.services;

import java.util.List;
import java.util.Optional;

public interface LocationService {
    ru.lomov.flashbackend.entities.Location createLocation(ru.lomov.flashbackend.entities.Location location);
    Optional<ru.lomov.flashbackend.entities.Location> getLocationById(String id);
    List<ru.lomov.flashbackend.entities.Location> getAllLocations();
    ru.lomov.flashbackend.entities.Location updateLocation(String id, ru.lomov.flashbackend.entities.Location location);
    void deleteLocation(String id);
}
