package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.BlockDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.BlockRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@Slf4j
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BlockServiceTest {
    private static final Long BLOCK_ID = 1L;
    private static final Long NEIGH_ID = 1L;
    @Mock private NeighborhoodService neighborhoodService;
    @Mock private BlockRepository blockRepository;
    @Captor private ArgumentCaptor<Block> captor;
    private BlockService blockService;
    private BlockDto blockDto;
    private Block block;
    private Neighborhood neighborhood;

    @BeforeEach
    void init() {
        blockService = new BlockService(blockRepository, neighborhoodService);
        // non-existent (fake) neighborhood
        neighborhood = new Neighborhood();
        neighborhood.setId(NEIGH_ID);

        // non-existent block
        block = new Block();
        block.setId(1L); // always needs to be set if you want to find it by id, either you'll have errors
        block.setName("A");
        block.setNeighborhood(neighborhood);

        // simulation of passed new block data
        blockDto = new BlockDto();
        blockDto.setName("D");
        blockDto.setNeighborhoodId(NEIGH_ID);
    }

    @Test
    void BlockService_GetBlockById_ReturnBlockDto() {
        // when
        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(block));
        BlockDto actualDto = blockService.getBlockDto(BLOCK_ID);

        // then
        assertThat(actualDto).isNotNull();
        assertThat(actualDto.getName()).isEqualTo("A");
        assertThat(actualDto.getNeighborhoodId()).isEqualTo(1L);
    }

    @Test
    void BlockService_GetAllBlocks_ReturnsBlockDtoList() {
        // when
        when(blockRepository.findAll())
                .thenReturn(Collections.singletonList(block));

        List<BlockDto> list = blockService.getAllBlocks();
        BlockDto foundBlock = list.getFirst();
        // then
        assertThat(foundBlock.getId()).isNotNull();
        assertThat(foundBlock.getName()).isEqualTo("A");
        assertThat(foundBlock.getNeighborhoodId()).isEqualTo(NEIGH_ID);
    }

    @Test
    void BlockService_CreateBlock_ReturnBlockDto() {

        when(neighborhoodService.getNeighborhood(anyLong())).thenReturn(neighborhood);
        when(blockRepository.save(any(Block.class))).thenReturn(block);

        // when
        blockService.createBlock(blockDto);// save block to repo
        verify(blockRepository, times(1)).save(captor.capture()); // check if saved once

        Block block = captor.getValue(); // capture saved object

        // then
        assertThat(block).isNotNull();
        assertThat(block.getName()).isEqualTo("D");
        assertThat(block.getNeighborhood()).isNotNull();
        assertThat(block.getNeighborhood().getId()).isEqualTo(1L);

        assertThat(block.getName()).isEqualTo(blockDto.getName());
    }

    @Test
    void BlockService_ThrowWhenGetById_ReturnNotFoundNeighborhood() {
        // when
        when(neighborhoodService.getNeighborhood(anyLong())).thenThrow(new NotFoundException("Neighborhood not found"));

        // then
        assertThat(blockDto.getName()).isEqualTo("D");
        assertThatThrownBy(() -> blockService.createBlock(blockDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Neighbor");
    }

    @Test
    void BlockService_ThrowWhenGetById_ReturnNotFoundDeveloper() {
        // when
        when(blockRepository.findById(anyLong())).thenThrow(new NotFoundException("Block not found"));

        // then
        assertThatThrownBy(() -> blockService.getBlock(anyLong()))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Block");
    }

    @Test
    void BlockService_UpdateBlock_ReturnBlockDto() {

        // when
        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(block));
        when(neighborhoodService.getNeighborhood(anyLong())).thenReturn(neighborhood);
        when(blockRepository.save(any(Block.class))).thenReturn(block);

        blockService.updateBlock(BLOCK_ID, blockDto);
        verify(blockRepository, times(1)).save(captor.capture());

        Block block = captor.getValue();
        // then
        assertThat(block.getName()).isEqualTo("D");
        assertThat(block.getNeighborhood().getId()).isEqualTo(NEIGH_ID);
    }

    @Test
    void BlockService_ThrowWhenUpdateById_ReturnNotFound() {
        when(neighborhoodService.getNeighborhood(anyLong())).thenThrow(new NotFoundException("Neighborhood not found"));

        // then
        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(block));
        assertThatThrownBy(() -> blockService.updateBlock(BLOCK_ID, blockDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Neighborhood");
    }

    @Test
    void BlockService_DeleteById_ReturnsNothing() {

        when(blockRepository.findById(anyLong())).thenReturn(Optional.of(block));

        assertAll(() -> blockService.deleteBlock(BLOCK_ID));
        verify(blockRepository).deleteById(BLOCK_ID);
        verifyNoMoreInteractions(blockRepository);

    }

    @Test
    void BlockService_ThrowWhenUpdateById_ReturnNotFoundBlock() {
        assertThatThrownBy(() -> blockService.updateBlock(BLOCK_ID, blockDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Block");
    }
}