package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.FlatDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FlatRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FlatService {
    private final FlatRepository flatRepository;
    private final OwnerService ownerService;
    private final BlockService blockService;
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
    public FlatDto createFlat(FlatDto dto) {
        Flat flat = new Flat();
        Owner foundOwner = ownerService.getOwner(dto.getOwnerId());
        Block foundBlock = blockService.getBlock(dto.getBlockId());
        flat.setALength(dto.getALength());
        flat.setBLength(dto.getBLength());
        flat.setOwner(foundOwner);
        flat.setBlock(foundBlock);
        return FlatDtoMapper.map(flat);
    }
}
