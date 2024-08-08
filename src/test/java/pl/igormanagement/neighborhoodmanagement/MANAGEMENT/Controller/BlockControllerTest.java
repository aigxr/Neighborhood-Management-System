package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.BlockDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.BlockService;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlockController.class)
@AutoConfigureMockMvc
class BlockControllerTest {
    private static final Long BLOCK_ID = 1L;
    private static final Long NEIGH_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BlockService blockService;

    private BlockDto blockDto;
    private Block block;
    private Neighborhood neighborhood;

    @BeforeEach
    void init() {
        // non-existent (fake) neighborhood
        neighborhood = new Neighborhood();
        neighborhood.setId(NEIGH_ID);

        // non-existent block
        block = new Block();
        block.setId(BLOCK_ID); // always needs to be set if you want to find it by id, either you'll have errors
        block.setName("A");
        block.setNeighborhood(neighborhood);

        // simulation of passed new block data
        blockDto = new BlockDto();
        blockDto.setId(BLOCK_ID);
        blockDto.setName("D");
        blockDto.setNeighborhoodId(NEIGH_ID);
    }

    @Test
    void BlockController_CreateBlock_ReturnsBlockDto() throws Exception {

        given(blockService.createBlock(ArgumentMatchers.any(BlockDto.class))).willReturn(blockDto);

        ResultActions response = mockMvc.perform(post("/create/block")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blockDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(blockDto.getName())))
                .andExpect(jsonPath("$.neighborhoodId", is(1)));
    }

    @Test
    void BlockController_UpdateBlockById_ReturnsBlockDto() throws Exception {
//        when(blockService.getBlock(BLOCK_ID)).thenReturn(block);
        given(blockService.updateBlock(anyLong(), any(BlockDto.class))).willReturn(blockDto);

        ResultActions response = mockMvc.perform(put("/update/block/" + BLOCK_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blockDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(blockDto.getName())))
                .andExpect(jsonPath("$.neighborhoodId", is(1)));
    }

    @Test
    void BlockController_GetAllBlocks_ReturnsListBlockDto() throws Exception {

        given(blockService.getAllBlocks()).willReturn(Collections.singletonList(blockDto));

        ResultActions response = mockMvc.perform(get("/blocks")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(blockDto.getName())))
                .andExpect(jsonPath("$[0].neighborhoodId", is(1)));
    }

    @Test
    void BlockController_GetBlockById_ReturnsBlockDto() throws Exception {

        given(blockService.getBlockDto(BLOCK_ID)).willReturn(blockDto);

        ResultActions response = mockMvc.perform(get("/block/" + BLOCK_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(blockDto.getName())))
                .andExpect(jsonPath("$.neighborhoodId", is(1)));
    }

    @Test
    void BlockController_DeleteBlockById_ReturnsStatusOK() throws Exception {
        doNothing().when(blockService).deleteBlock(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/block/" + BLOCK_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
        verify(blockService).deleteBlock(BLOCK_ID);
    }
}