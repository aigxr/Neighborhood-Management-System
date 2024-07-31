package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.NeighborhoodService;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.NeighborhoodRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NeighborhoodController.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@AutoConfigureMockMvc
class NeighborhoodControllerTest {
    private static final Long NEIGH_ID = 1L;
    private static final Long DEVELOPER_ID = 1L;

    @MockBean NeighborhoodService neighborhoodService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    private Developer developer;
    private NeighborhoodDto neighborhoodDto;
    private Neighborhood neighborhood;

    @BeforeEach
    void init() {
        // simulation of non-existent developer
        developer = new Developer();
        developer.setId(DEVELOPER_ID);
        developer.setFirstName("Jan");
        developer.setLastName("Kowalski");

        // simulation of non-existent current neighborhood
        neighborhood = new Neighborhood();
        neighborhood.setId(NEIGH_ID);
        neighborhood.setDeveloper(developer);
        neighborhood.setName("SECURITY");
        neighborhood.setCity("Łódź");
        neighborhood.setDeveloper(developer);

        // simulation of data passed by user
        neighborhoodDto = new NeighborhoodDto();
        neighborhoodDto.setDeveloperId(DEVELOPER_ID);
        neighborhoodDto.setName("SOLID");
        neighborhoodDto.setCity("Warszawa");
        neighborhoodDto.setAddress("Some street");
    }

    @Test
    void NeighborhoodController_CreateNeighborhood_ReturnsDto() throws Exception {

        given(neighborhoodService.createNeighborhood(any(NeighborhoodDto.class))).willReturn(neighborhoodDto);

        ResultActions response = mockMvc.perform(post("/create/neighborhood")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(neighborhoodDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.developerId", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is(neighborhoodDto.getName())))
                .andExpect(jsonPath("$.city", CoreMatchers.is(neighborhoodDto.getCity())))
                .andExpect(jsonPath("$.address", CoreMatchers.is(neighborhoodDto.getAddress())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void NeighborhoodController_GetAllNeighborhoods_ReturnsDto() throws Exception {

        List<NeighborhoodDto> list = Collections.singletonList(neighborhoodDto);

        when(neighborhoodService.getAllNeighborhoods()).thenReturn(list);

        ResultActions response = mockMvc.perform(get("/neighborhood")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void NeighborhoodController_GetAllNeighborhoodsByDeveloperId_ReturnsDto() throws Exception {

        List<NeighborhoodDto> list = Collections.singletonList(neighborhoodDto);

        when(neighborhoodService.getNeighborhoodsByDeveloperId(1L)).thenReturn(list);

        ResultActions response = mockMvc.perform(get("/neighborhood/developer/" + DEVELOPER_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].developerId", CoreMatchers.is(1))) // that's how you test list
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void NeighborhoodController_GetNeighborhood_ReturnsDto() throws Exception {

        when(neighborhoodService.getNeighborhoodDto(anyLong())).thenReturn(neighborhoodDto);

        ResultActions response = mockMvc.perform(get("/neighborhood/" + NEIGH_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.developerId", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is(neighborhoodDto.getName())))
                .andExpect(jsonPath("$.city", CoreMatchers.is(neighborhoodDto.getCity())))
                .andExpect(jsonPath("$.address", CoreMatchers.is(neighborhoodDto.getAddress())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void NeighborhoodController_UpdateNeighborhood_ReturnsDto() throws Exception {

        given(neighborhoodService.updateNeighborhood(anyLong(), any(NeighborhoodDto.class))).willReturn(neighborhoodDto);

        ResultActions response = mockMvc.perform(put("/update/neighborhood/" + NEIGH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(neighborhoodDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.developerId", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is(neighborhoodDto.getName())))
                .andExpect(jsonPath("$.city", CoreMatchers.is(neighborhoodDto.getCity())))
                .andExpect(jsonPath("$.address", CoreMatchers.is(neighborhoodDto.getAddress())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void NeighborhoodController_DeleteNeighborhoodById_ReturnsNothing() throws Exception {
        doNothing().when(neighborhoodService).deleteNeighborhood(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/neighborhood/" + NEIGH_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
        verify(neighborhoodService).deleteNeighborhood(NEIGH_ID);
    }
}