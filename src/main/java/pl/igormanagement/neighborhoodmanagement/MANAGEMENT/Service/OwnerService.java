package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.FlatDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.OwnerDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FlatRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.OwnerRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final FlatRepository flatRepository;

    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream().map(OwnerDtoMapper::map).toList();
    }

    public OwnerDtoResponse getOwnerDtoResponse(Long id) {
        List<FlatDtoResponse> allFlats = flatRepository
                .findAllByOwnerId(id).stream().map(FlatDtoMapper::response).toList();

        OwnerDtoResponse foundOwner = ownerRepository
                .findById(id).map(OwnerDtoMapper::response)
                .orElseThrow(() -> new NotFoundException("Owner not found"));

        foundOwner.setFlats(allFlats);
        return foundOwner;
    }

    public OwnerDto getOwnerDto(Long id) {
        return ownerRepository.findById(id).map(OwnerDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Owner not found"));
    }

    public Owner getOwner(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Owner not found"));
    }

    @Transactional
    public OwnerDto createOwner(OwnerDto dto) {
        Owner savedOwner = ownerRepository.save(OwnerDtoMapper.map(dto));
        return OwnerDtoMapper.map(savedOwner);
    }

    @Transactional
    public OwnerDto updateOwner(Long id, OwnerDto dto) {
        Owner foundOwner = getOwner(id);
        Owner mappedOwner = OwnerDtoMapper.updateMap(foundOwner, dto);

        Owner savedOwner = ownerRepository.save(mappedOwner);
        return OwnerDtoMapper.map(savedOwner);
    }

    @Transactional
    public void deleteOwner(Long id) {
        Owner owner = getOwner(id);
        ownerRepository.deleteById(owner.getId());
    }
}
