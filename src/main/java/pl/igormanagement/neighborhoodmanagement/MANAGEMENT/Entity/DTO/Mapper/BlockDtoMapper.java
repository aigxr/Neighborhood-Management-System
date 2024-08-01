package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.BlockDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.BlockDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;

public class BlockDtoMapper {
    public static BlockDto map(Block block) {
        BlockDto dto = new BlockDto();
        dto.setId(block.getId());
        dto.setName(block.getName());
        dto.setNeighborhoodId(block.getNeighborhood().getId());
        return dto;
    }

    public static BlockDtoResponse response(Block block) {
        BlockDtoResponse dto = new BlockDtoResponse();
        dto.setId(block.getId());
        dto.setName(block.getName());
        return dto;
    }

//    public static Block map(BlockDto dto) {
//        Block block = new Block();
//        block.setId(dto.getId());
//        block.setName(dto.getName());
//        return block;
//    }
}
