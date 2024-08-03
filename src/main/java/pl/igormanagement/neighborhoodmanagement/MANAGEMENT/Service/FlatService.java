package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.FlatDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.RoomDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RoomDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FlatRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.RoomRepository;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlatService {
    private final FlatRepository flatRepository;
    private final OwnerService ownerService;
    private final BlockService blockService;
    private final TenantService tenantService;
    private final RoomService roomService;
    private final ParkingService parkingService;
    public List<FlatDtoResponse> getAllFlats() {
        return flatRepository.findAll().stream().map(FlatDtoMapper::response).toList();
    }

    public FlatDtoResponse getFlatDto(Long id) {
        return flatRepository.findById(id).map(FlatDtoMapper::response)
                .orElseThrow(() -> new NotFoundException("Flat not found"));
    }

    public Flat getFlat(Long id) {
        return flatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flat not found"));
    }

    @Transactional
    public FlatDtoResponse createFlat(FlatDto dto) {
        Room room = roomService.createRoom(dto);

        Owner foundOwner = ownerService.getOwner(dto.getOwnerId());
        Block foundBlock = blockService.getBlock(dto.getBlockId());
        Tenant foundTenant = dto.getTenantId() != null ? tenantService.getTenant(dto.getTenantId()) : null;


        Flat flat = new Flat();
        flat.setName(dto.getName());
        flat.setOwner(foundOwner);
        flat.setBlock(foundBlock);
        flat.setTenant(foundTenant);
        flat.setRoom(room);
        Flat savedFlat = flatRepository.save(flat);

        return FlatDtoMapper.response(savedFlat);
    }


    @Transactional
    public FlatDtoResponse updateFlat(Long id, FlatDto dto) {
        Flat foundFlat = getFlat(id);
        Room foundRoom = foundFlat.getRoom();

        // this is specific mapper for changing only a length and b length
        Room mappedRoom = RoomDtoMapper.mapFoundRoom(foundRoom, dto.getALength(), dto.getBLength());
        // if any data about room like length is passed then room changes.

        Owner foundOwner = ownerService.getOwner(dto.getOwnerId());
        Block foundBlock = blockService.getBlock(dto.getBlockId());
        Tenant foundTenant = dto.getTenantId() != null ? tenantService.getTenant(dto.getTenantId()) : null;

        if (dto.getName() != null)
            foundFlat.setName(dto.getName());
        if (dto.getBlockId() != null)
            foundFlat.setBlock(foundBlock);
        if (dto.getOwnerId() != null)
            foundFlat.setOwner(foundOwner);
        foundFlat.setTenant(foundTenant);
        foundFlat.setRoom(mappedRoom);

        Flat savedFlat = flatRepository.save(foundFlat);

        return FlatDtoMapper.response(savedFlat);
    }

    @Transactional
    public void deleteFlat(Long id) {
        Flat foundFlat = getFlat(id); // room id cannot be null that's why exception is here
        flatRepository.deleteById(foundFlat.getId()); // needs to be first otherwise it will violate not null constraint
        roomService.deleteRoom(foundFlat.getRoom().getId());
    }

    @Transactional
    public void buyParkingSpace(Long flatId) {
        // RANDOM ASS GENERATOR
        Random random = new Random();
        Flat foundFlat = getFlat(flatId);

        List<Parking> allAvailableParking = parkingService.getAllAvailableParking();

        Parking randomParking = allAvailableParking.get(random.nextInt(1, allAvailableParking.size()));

        foundFlat.setParking(randomParking);

        flatRepository.save(foundFlat);
    }
}
