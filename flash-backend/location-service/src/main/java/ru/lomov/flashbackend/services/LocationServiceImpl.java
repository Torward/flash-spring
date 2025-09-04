package ru.lomov.flashbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lomov.flashbackend.entities.Location;
import ru.lomov.flashbackend.repositories.LocationRepository;
import ru.lomov.flashbackend.exceptions.LocationNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location createLocation(Location location) {
        if (location.getId() == null) {
            location.setId(UUID.randomUUID().toString());
        }
        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> getLocationById(String id) {
        return locationRepository.findById(id);
    }

    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location updateLocation(String id, Location location) {
        Location existingLocation = locationRepository.findById(id)
            .orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));

        // Update fields
        existingLocation.setName(location.getName());
        existingLocation.setDescription(location.getDescription());
        existingLocation.setLatitude(location.getLatitude());
        existingLocation.setLongitude(location.getLongitude());

        return locationRepository.save(existingLocation);
    }

    @Override
    public void deleteLocation(String id) {
        Location location = locationRepository.findById(id)
            .orElseThrow(() -> new LocationNotFoundException("Location not found with id: " + id));

        locationRepository.delete(location);
    }
}
