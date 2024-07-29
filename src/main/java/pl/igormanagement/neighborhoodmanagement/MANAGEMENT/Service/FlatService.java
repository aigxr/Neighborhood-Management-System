package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.FlatDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.RoomDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FlatRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.RoomRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlatService {
    private final FlatRepository flatRepository;
    private final OwnerService ownerService;
    private final BlockService blockService;
    private final TenantService tenantService;
    private final RoomRepository roomRepository;
    public List<FlatDto> getAllFlats() {
        return flatRepository.findAll().stream().map(FlatDtoMapper::map).toList();
    }

    public FlatDto getFlatDto(Long id) {
        return flatRepository.findById(id).map(FlatDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Flat not found"));
    }

    public Flat getFlat(Long id) {
        return flatRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Flat not found"));
    }

    @Transactional
    public FlatDtoResponse createFlat(FlatDto dto) {
        Room mappedRoom = RoomDtoMapper.map(dto.getALength(), dto.getBLength());
        roomRepository.save(mappedRoom);

        Owner foundOwner = ownerService.getOwner(dto.getOwnerId());
        Block foundBlock = blockService.getBlock(dto.getBlockId());
        Tenant foundTenant = tenantService.getTenant(dto.getTenantId());

        Flat flat = new Flat();
        flat.setName(dto.getName());
        flat.setOwner(foundOwner);
        flat.setBlock(foundBlock);
        flat.setTenant(foundTenant);
        flat.setRoom(mappedRoom);
        Flat savedFlat = flatRepository.save(flat);

        return FlatDtoMapper.response(savedFlat);
    }
}
