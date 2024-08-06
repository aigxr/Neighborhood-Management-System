package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.FlatDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.FlatService;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FlatController.class)
@AutoConfigureMockMvc
class FlatControllerTest {
    private static final Long FLAT_ID = 1L;
    private static final Long OWNER_ID = 1L;
    private static final Long BLOCK_ID = 1L;
    private static final Long TENANT_ID = 1L;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlatService flatService;

    private Owner owner;
    private Room room;
    private Block block;
    private Tenant tenant;
    private Flat flat;
    private FlatDto flatDto;
    private FlatDtoResponse flatDtoResponse;

    @BeforeEach
    void init() {
        // all simulation is defined here
        room = new Room();
        room.setId(1L);
        room.setALength(30.0);
        room.setBLength(40.0);

        owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setFirstName("John");

        block = new Block();
        block.setId(BLOCK_ID);
        block.setName("Block Name");

        tenant = new Tenant();
        tenant.setId(TENANT_ID);
        tenant.setFirstName("John");

        flat = new Flat();
        flat.setId(FLAT_ID);
        flat.setName("House");
        flat.setOwner(owner);
        flat.setBlock(block);
        flat.setTenant(tenant);
        flat.setRoom(room);

        // data to change / create a new flat
        flatDto = new FlatDto();
        flatDto.setId(FLAT_ID);
        flatDto.setName("Apartment");
        // data for creating a new room
        flatDto.setALength(30.0);
        flatDto.setBLength(40.0);
        flatDto.setOwnerId(OWNER_ID);
        flatDto.setBlockId(BLOCK_ID);
        flatDto.setTenantId(TENANT_ID);

        flatDtoResponse = new FlatDtoResponse();
        flatDtoResponse.setId(FLAT_ID);
        flatDtoResponse.setName("Apartment");
        flatDtoResponse.setOwnerId(OWNER_ID);
        flatDtoResponse.setBlockId(BLOCK_ID);
        flatDtoResponse.setTenantId(null);
        flatDtoResponse.setRoom(room);

    }

    @Test
    void FlatController_CreateFlat_ReturnResponseDto() throws Exception {
        given(flatService.createFlat(any(FlatDto.class))).willReturn(flatDtoResponse); // because we return optimized info

        ResultActions response = mockMvc.perform(post("/create/flat")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flatDto)));

        verify(flatService).createFlat(any(FlatDto.class));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Apartment")))
                .andExpect(jsonPath("$.room.alength", is(30.0)))
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    void FlatController_UpdateFlatById_ReturnResponseDto() throws Exception {
        given(flatService.updateFlat(anyLong(), any(FlatDto.class))).willReturn(flatDtoResponse); // because we return optimized info

        ResultActions response = mockMvc.perform(put("/update/flat/" + FLAT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(flatDto)));

        verify(flatService).updateFlat(anyLong(), any(FlatDto.class));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Apartment")))
                .andExpect(jsonPath("$.room.alength", is(30.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void FlatController_DeleteFlatByAndRoomId_ReturnNothing() throws Exception {
        doNothing().when(flatService).deleteFlat(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/flat/" + FLAT_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());

        verify(flatService).deleteFlat(FLAT_ID);
        verifyNoMoreInteractions(flatService);
    }

    @Test
    void FlatController_GetAllFlats_ReturnListResponseDto() throws Exception {
        given(flatService.getAllFlats()).willReturn(Collections.singletonList(flatDtoResponse));

        ResultActions response = mockMvc.perform(get("/flats")
                .contentType(MediaType.APPLICATION_JSON));

        verify(flatService).getAllFlats();

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Apartment")))
                .andExpect(jsonPath("$[0].room.alength", is(30.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void FlatController_GetFlatById_ReturnResponseDto() throws Exception {
        given(flatService.getFlatDto(anyLong())).willReturn(flatDtoResponse);

        ResultActions response = mockMvc.perform(get("/flat/" + FLAT_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(flatService).getFlatDto(anyLong());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Apartment")))
                .andExpect(jsonPath("$.room.alength", is(30.0)))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void FlatController_RentParkingForFlat_ReturnMessage() throws Exception {
        ResultActions response = mockMvc.perform(post("/flat/" + FLAT_ID + "/rent/parking")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Parking successfully rented")));
    }

    @Test
    void FlatController_AssignTenantToAFlat_ReturnMessage() throws Exception {
        ResultActions response = mockMvc.perform(post("/flat/" + FLAT_ID + "/assign/tenant/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Tenant successfully assigned")));
    }

    @Test
    void FlatController_RemoveTenantToAFlat_ReturnMessage() throws Exception {
        ResultActions response = mockMvc.perform(post("/flat/" + FLAT_ID + "/remove/tenant") // without tenants id
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Tenant successfully removed")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void FlatController_AssignPersonToAFlat_ReturnMessage() throws Exception {
        ResultActions response = mockMvc.perform(post("/flat/" + FLAT_ID + "/assign/person/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Person successfully assigned")));
    }

    @Test
    void FlatController_RemovePersonToAFlat_ReturnMessage() throws Exception {
        ResultActions response = mockMvc.perform(post("/flat/" + FLAT_ID + "/remove/person/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Person successfully removed")));
    }
}