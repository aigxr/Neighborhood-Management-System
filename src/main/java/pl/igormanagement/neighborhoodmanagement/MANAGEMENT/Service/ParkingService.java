package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.ParkingDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.ParkingRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository parkingRepository;

    public List<ParkingDto> getAllParking() {
        return parkingRepository.findAll().stream().map(ParkingDtoMapper::map).toList();
    }

    public ParkingDto getParkingDto(Long id) {
        return parkingRepository.findById(id).map(ParkingDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Parking not found"));
    }

    public Parking getParking(Long id) {
        return parkingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Parking not found"));
    }

    public ParkingDto createParking() {
        Parking parking = new Parking();
        return ParkingDtoMapper.map(parking);
    }
}
