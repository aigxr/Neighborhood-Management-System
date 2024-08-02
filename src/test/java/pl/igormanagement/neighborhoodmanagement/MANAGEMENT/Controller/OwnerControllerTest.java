package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.OwnerService;

import java.time.LocalDate;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OwnerController.class)
@AutoConfigureMockMvc
class OwnerControllerTest {
    private static final Long OWNER_ID = 1L;

    @MockBean
    private OwnerService ownerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private FlatDtoResponse flatDtoResponse;
    private Owner owner;
    private OwnerDto ownerDto;
    private OwnerDtoResponse ownerDtoResponse;

    @BeforeEach
    void init() {
        // DATA SAVED TO REPO
        owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setFirstName("John");
        owner.setLastName("Kowalski");

        // FULL DATA PASSED BY USER
        ownerDto = new OwnerDto();
        ownerDto.setFirstName("Igor");
        ownerDto.setLastName("Nowak");
        ownerDto.setPesel(12341235341L);
        ownerDto.setAddress("Some street");
        ownerDto.setBirthDate(LocalDate.of(2000, 2, 2));
    }

    @Test
    void OwnerController_CreateOwner_ReturnOwnerDto() throws Exception {
        given(ownerService.createOwner(any(OwnerDto.class))).willReturn(ownerDto);

        ResultActions response = mockMvc.perform(post("/create/owner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ownerDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void OwnerController_UpdateOwnerById_ReturnOwnerDto() throws Exception {
        when(ownerService.getOwner(anyLong())).thenReturn(owner);
        given(ownerService.updateOwner(anyLong(), any(OwnerDto.class))).willReturn(ownerDto);

        ResultActions response = mockMvc.perform(put("/update/owner/" + OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ownerDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void OwnerController_DeleteOwnerById_ReturnNothing() throws Exception {
        doNothing().when(ownerService).deleteOwner(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/owner/" + OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(ownerService).deleteOwner(anyLong());

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void OwnerController_GetOwnerById_ReturnOwnerDto() throws Exception {
        ownerDtoResponse = new OwnerDtoResponse();
        ownerDtoResponse.setId(OWNER_ID);
        ownerDtoResponse.setFirstName("Igor");
        ownerDtoResponse.setLastName("Nowak");

        given(ownerService.getOwnerDtoResponse(anyLong())).willReturn(ownerDtoResponse);

        ResultActions response = mockMvc.perform(get("/owner/" + OWNER_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(ownerService).getOwnerDtoResponse(anyLong());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    void OwnerController_GetAllOwners_ReturnListOwnerDto() throws Exception {
        given(ownerService.getAllOwners()).willReturn(Collections.singletonList(ownerDto));

        ResultActions response = mockMvc.perform(get("/owners")
                .contentType(MediaType.APPLICATION_JSON));

        verify(ownerService).getAllOwners();

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Nowak")))
                .andDo(MockMvcResultHandlers.print());
    }
}