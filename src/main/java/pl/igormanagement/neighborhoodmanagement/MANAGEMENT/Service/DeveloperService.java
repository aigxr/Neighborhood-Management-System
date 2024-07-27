package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.DeveloperDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.DeveloperRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public List<DeveloperDto> getAllDevelopers() {
        return developerRepository.findAll().stream().map(DeveloperDtoMapper::map).toList();
    }

    public DeveloperDto getDeveloperDto(Long id) {
        return developerRepository.findById(id)
                .map(DeveloperDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Developer not found"));
    }
    public Developer getDeveloper(Long id) {
        return developerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Developer not found"));
    }
    @Transactional
    public DeveloperDto createDeveloper(DeveloperDto dto) {
        Developer developer = new Developer();
        developer.setFirstName(dto.getFirstName());
        developer.setLastName(dto.getLastName());
        Developer savedDeveloper = developerRepository.save(developer);
        return DeveloperDtoMapper.map(savedDeveloper);
    }

    @Transactional
    public DeveloperDto updateDeveloper(Long id, DeveloperDto dto) {
        Developer developer = getDeveloper(id);
        if (dto.getFirstName() != null) {
            developer.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            developer.setLastName(dto.getLastName());
        }
        Developer savedDeveloper = developerRepository.save(developer);
        return DeveloperDtoMapper.map(savedDeveloper);
    }

    @Transactional
    public void deleteDeveloper(Long id) {
        Developer developer = getDeveloper(id);
        developerRepository.deleteById(developer.getId());
    }
}
