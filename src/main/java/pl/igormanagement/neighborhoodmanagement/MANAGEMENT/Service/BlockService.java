package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.AlreadyExistsException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.OversizeException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.BlockDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.BlockDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.BlockRepository;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlockService {
    private final BlockRepository blockRepository;
    private final NeighborhoodService neighborhoodService;

    public List<BlockDto> getAllBlocks() {
        return blockRepository.findAll().stream().map(BlockDtoMapper::map).toList();
    }

    public BlockDto getBlockDto(Long id) {
        return blockRepository.findById(id)
                .map(BlockDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Block not found"));
    }
    public Block getBlock(Long id) {
        return blockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Block not found"));
    }

    @Transactional
    public BlockDto createBlock(BlockDto dto) {

        List<Block> allBlocks = blockRepository.findAll();
        if (allBlocks.size() == 6) {
            throw new OversizeException(String
                    .format("Maximum size of blocks is 6 - current (%s)", allBlocks.size()));
        }

        findBlockByIdAndName(dto);

        Block block = new Block();
        Neighborhood foundNeighborhood = neighborhoodService
                .getNeighborhood(dto.getNeighborhoodId());
        block.setName(dto.getName());
        block.setNeighborhood(foundNeighborhood);
        Block savedBlock = blockRepository.save(block);
        return BlockDtoMapper.map(savedBlock);
    }

    @Transactional
    public BlockDto updateBlock(Long id, BlockDto dto) {

        findBlockByIdAndName(dto);

        Block foundBlock = getBlock(id);
        if (dto.getNeighborhoodId() != null) {
            Neighborhood foundNeighborhood = neighborhoodService
                    .getNeighborhood(dto.getNeighborhoodId());
            foundBlock.setNeighborhood(foundNeighborhood);
        }
        if (dto.getName() != null)
            foundBlock.setName(dto.getName());
        Block savedBlock = blockRepository.save(foundBlock);
        return BlockDtoMapper.map(savedBlock);
    }

    @Transactional
    public void deleteBlock(Long id) {
        Block block = getBlock(id);
        blockRepository.deleteById(block.getId());
    }

    private void findBlockByIdAndName(BlockDto dto) {
        Optional<Block> foundBlock = blockRepository
                .findByNameAndNeighborhoodId(dto.getName(), dto.getNeighborhoodId());
        if (foundBlock.isPresent()) {
            throw new AlreadyExistsException(String
                    .format("Block %s already exists in neighborhood %s", dto.getName(), dto.getNeighborhoodId()));
        }
    }
}
