package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.RoomDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RoomDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.RoomRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public List<RoomDto> getAllRooms() {
        return roomRepository.findAll().stream().map(RoomDtoMapper::map).toList();
    }

    public RoomDto getRoomDto(Long id) {
        return roomRepository.findById(id).map(RoomDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Room not found"));
    }

    public Room getRoom(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room not found"));
    }
    @Transactional
    public Room createRoom(FlatDto dto) {
        Room mappedRoom = RoomDtoMapper.map(dto.getALength(), dto.getBLength());
        return roomRepository.save(mappedRoom);
    }

    @Transactional
    public Room createRoom(ParkingDto dto) {
        Room mappedRoom = RoomDtoMapper.map(dto.getALength(), dto.getBLength());
        return roomRepository.save(mappedRoom);
    }

    @Transactional
    public void deleteRoom(Long id) {
        Room foundRoom = getRoom(id);
        roomRepository.deleteById(foundRoom.getId());
    }
}
