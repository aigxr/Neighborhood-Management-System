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
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.ParkingService;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ParkingController.class)
@AutoConfigureMockMvc
class ParkingControllerTest {
    private static final Long PARKING_ID = 1L;

    @MockBean
    private ParkingService parkingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Parking parking;
    private ParkingDto parkingDto;
    private ParkingDtoResponse parkingDtoResponse;
    private Room room;

    @BeforeEach
    void init() {
        parkingDto = new ParkingDto();
        parkingDto.setId(PARKING_ID);
        parkingDto.setName("A");
        parkingDto.setALength(3.0);
        parkingDto.setBLength(1.5);

        room = new Room();
        room.setALength(parkingDto.getALength());
        room.setBLength(parkingDto.getBLength());

        // required to return data to client
        parkingDtoResponse = new ParkingDtoResponse();
        parkingDtoResponse.setId(PARKING_ID);
        parkingDtoResponse.setName("A");
        parkingDtoResponse.setRoom(room);

        parking = new Parking();
        parking.setId(PARKING_ID);
        parking.setName("A");
        parking.setRoom(room);
    }

    @Test
    void ParkingController_CreateParking_ReturnParkingDtoResponse() throws Exception {
        given(parkingService.createParking(any(ParkingDto.class))).willReturn(parkingDtoResponse);

        ResultActions response = mockMvc.perform(post("/create/parking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("A")))
                .andExpect(jsonPath("$.room.alength", CoreMatchers.is(3.0)));
    }

    @Test
    void ParkingController_UpdateParkingById_ReturnParkingDtoResponse() throws Exception {
        given(parkingService.updateParking(anyLong(), any(ParkingDto.class))).willReturn(parkingDtoResponse);

        ResultActions response = mockMvc.perform(put("/update/parking/" + PARKING_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(parkingDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("A")))
                .andExpect(jsonPath("$.room.alength", CoreMatchers.is(3.0)));
    }

    @Test
    void ParkingController_DeleteParkingById_ReturnNothing() throws Exception {
        doNothing().when(parkingService).deleteParking(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/parking/" + PARKING_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(parkingService).deleteParking(anyLong());

        response.andExpect(status().isOk());
    }

    @Test
    void ParkingController_GetParkingById_ReturnParkingDtoResponse() throws Exception {
        given(parkingService.getParkingDtoResponse(anyLong())).willReturn(parkingDtoResponse);

        ResultActions response = mockMvc.perform(get("/parking/" + PARKING_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(parkingService).getParkingDtoResponse(anyLong());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$.name", CoreMatchers.is("A")))
                .andExpect(jsonPath("$.room.alength", CoreMatchers.is(3.0)));
    }

    @Test
    void ParkingController_GetParkingDtoList_ReturnListParkingDtoResponse() throws Exception {
        given(parkingService.getAllParking()).willReturn(Collections.singletonList(parkingDtoResponse));

        ResultActions response = mockMvc.perform(get("/parking")
                .contentType(MediaType.APPLICATION_JSON));

        verify(parkingService).getAllParking();

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.is(1)))
                .andExpect(jsonPath("$[0].name", CoreMatchers.is("A")))
                .andExpect(jsonPath("$[0].room.alength", CoreMatchers.is(3.0)))
                .andExpect(jsonPath("$[0].room.size()", CoreMatchers.is(4)));
    }
}