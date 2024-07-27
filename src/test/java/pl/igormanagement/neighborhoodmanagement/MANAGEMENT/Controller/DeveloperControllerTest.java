package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.DeveloperService;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = DeveloperController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class DeveloperControllerTest {

    private static final Long DEVELOPER_ID = 1L;

    @MockBean
    private DeveloperService developerService;
    // @MockBean can interact with spring context, @Mock does not

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    private Developer developer;
    private DeveloperDto developerDto;

    @BeforeEach
    void init() {
        developer = new Developer();
        // given data for simulation
        developer.setId(DEVELOPER_ID);
        developer.setFirstName("Jan");
        developer.setLastName("Kowalski");
        developerDto = new DeveloperDto();
        // simulation of real data passed by a user
        developerDto.setId(DEVELOPER_ID);
        developerDto.setFirstName("Igor");
        developerDto.setLastName("Siotor");
    }

    @Test
    void DeveloperController_CreateDeveloper_ReturnCreated() throws Exception {
        given(developerService.createDeveloper(any(DeveloperDto.class))).willReturn(developerDto);

        ResultActions response = mockMvc.perform(post("/add/developer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developerDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(developerDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(developerDto.getLastName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void DeveloperController_GetAllDevelopers_ReturnDto() throws Exception {
        when(developerService.getAllDevelopers()).thenReturn(Collections.singletonList(developerDto));

        ResultActions response = mockMvc.perform(get("/developer")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void DeveloperController_GetDeveloperById_ReturnDeveloperDto() throws Exception {
        when(developerService.getDeveloperDto(DEVELOPER_ID)).thenReturn(developerDto);

        ResultActions response = mockMvc.perform(get("/developer/" + DEVELOPER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developerDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void DeveloperController_UpdateDeveloperById_ReturnDeveloperDto() throws Exception {
        when(developerService.updateDeveloper(anyLong(), any(DeveloperDto.class))).thenReturn(developerDto);

        ResultActions response = mockMvc.perform(patch("/update/developer/" + DEVELOPER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developerDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(developerDto.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(developerDto.getLastName())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void DeveloperController_DeleteDeveloperById_ReturnNothing() throws Exception {
        doNothing().when(developerService).deleteDeveloper(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/developer/" + DEVELOPER_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
        verify(developerService).deleteDeveloper(DEVELOPER_ID);
    }
}