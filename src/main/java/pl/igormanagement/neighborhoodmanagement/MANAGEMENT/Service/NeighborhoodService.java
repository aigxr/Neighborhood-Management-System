package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.BlockDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.BlockDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.NeighborhoodDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.BlockRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.NeighborhoodRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class NeighborhoodService {
    private final NeighborhoodRepository neighborhoodRepository;
    private final DeveloperService developerService;
    private final BlockRepository blockRepository;

    public List<NeighborhoodDto> getAllNeighborhoods() {
        return neighborhoodRepository.findAll().stream().map(NeighborhoodDtoMapper::map).toList();
    }

    public List<NeighborhoodDto> getNeighborhoodsByDeveloperId(Long id) {
        Developer foundDeveloper = developerService.getDeveloper(id);
        return neighborhoodRepository.findAllByDeveloperId(foundDeveloper.getId())
                .stream().map(NeighborhoodDtoMapper::map).toList();
    }

    public NeighborhoodDtoResponse getNeighborhoodDtoResponse(Long id) {
        NeighborhoodDtoResponse foundResponse = neighborhoodRepository
                .findById(id).map(NeighborhoodDtoMapper::response)
                .orElseThrow(() -> new NotFoundException("Neighborhood not found"));

        List<BlockDtoResponse> allBlocks = blockRepository
                .findAllByNeighborhoodId(id).stream().map(BlockDtoMapper::response).toList();

        foundResponse.setBlockList(allBlocks);
        return foundResponse;
    }

    public NeighborhoodDto getNeighborhoodDto(Long id) {
        return neighborhoodRepository.findById(id).map(NeighborhoodDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Neighborhood not found"));
    }

    public Neighborhood getNeighborhood(Long id) {
        return neighborhoodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Neighborhood not found"));
    }

    @Transactional
    public NeighborhoodDto createNeighborhood(NeighborhoodDto dto) {
        Neighborhood neighborhood = new Neighborhood();
        Developer developer = developerService.getDeveloper(dto.getDeveloperId());
        neighborhood.setDeveloper(developer);
        neighborhood.setName(dto.getName());
        neighborhood.setCity(dto.getCity());
        neighborhood.setAddress(dto.getAddress());
        Neighborhood savedDto = neighborhoodRepository.save(neighborhood);
        return NeighborhoodDtoMapper.map(savedDto);
    }

    @Transactional
    public NeighborhoodDto updateNeighborhood(Long id, NeighborhoodDto dto) {
        Neighborhood neighborhood = getNeighborhood(id);
        Developer developer = developerService.getDeveloper(dto.getDeveloperId());
        Neighborhood mappedNeighborhood = NeighborhoodDtoMapper.map(neighborhood, dto, developer);
        Neighborhood savedDto = neighborhoodRepository.save(mappedNeighborhood);
        return NeighborhoodDtoMapper.map(savedDto);
    }

    @Transactional
    public void deleteNeighborhood(Long id) {
        Neighborhood foundNeighborhood = getNeighborhood(id);
        neighborhoodRepository.deleteById(foundNeighborhood.getId());
    }
}
