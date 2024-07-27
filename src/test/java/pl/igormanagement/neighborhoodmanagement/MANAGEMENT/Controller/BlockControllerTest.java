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

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BlockController.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
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
                .andExpect(jsonPath("$.neighborhoodId", is(1)))
                .andDo(MockMvcResultHandlers.print());
    }
}