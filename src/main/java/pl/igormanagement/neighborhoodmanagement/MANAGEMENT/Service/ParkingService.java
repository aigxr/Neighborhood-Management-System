package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.AlreadyExistsException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.ParkingDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.ParkingRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.RoomRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final RoomService roomService;

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

    @Transactional
    public ParkingDtoResponse createParking(ParkingDto dto) {
        Parking parking = new Parking();
        parking.setId(dto.getId());

        Optional<Parking> foundParking = parkingRepository.findByName(dto.getName());
        if (foundParking.isPresent()) {
            throw new AlreadyExistsException(String.format("Parking with name %s already exists", dto.getName()));
        }

        parking.setName(dto.getName());

        Room createdRoom = roomService.createRoom(dto);
        parking.setRoom(createdRoom);
        Parking savedParking = parkingRepository.save(parking);
        return ParkingDtoMapper.response(savedParking);
    }

    @Transactional
    public void deleteParking(Long id) {
        Parking foundParking = getParking(id);
        parkingRepository.deleteById(foundParking.getId());
        roomService.deleteRoom(foundParking.getRoom().getId());
    }
}
