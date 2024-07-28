package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.OwnerDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.OwnerRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public List<OwnerDto> getAllOwners() {
        return ownerRepository.findAll().stream().map(OwnerDtoMapper::map).toList();
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
}
