package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.VehicleService;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.Vehicle;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.VehicleDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = VehicleController.class)
class VehicleControllerTest {
    private static final Long VEHICLE_ID = 1L;

    @MockBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    private Vehicle vehicle;
    private VehicleDto vehicleDto;

    @BeforeEach
    void init() {
        // normal vehicle
        vehicle = new Vehicle();
        vehicle.setId(VEHICLE_ID);
        vehicle.setName("Car");
        vehicle.setALength(2.0);
        vehicle.setBLength(1.2);

        vehicleDto = new VehicleDto();
        vehicleDto.setId(VEHICLE_ID);
        vehicleDto.setName("Vehicle");
        vehicleDto.setEngine("Engine");
        vehicleDto.setALength(3.0);
        vehicleDto.setBLength(1.2);
    }

    @Test
    void VehicleController_CreateVehicle_ReturnVehicle() throws Exception{
        given(vehicleService.createVehicle(any(VehicleDto.class))).willReturn(vehicle);

        ResultActions response = mockMvc.perform(post("/create/vehicle")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Car")));
    }

    @Test
    void VehicleController_UpdateVehicleById_ReturnVehicle() throws Exception{
        given(vehicleService.updateVehicle(anyLong(), any(VehicleDto.class))).willReturn(vehicle);

        ResultActions response = mockMvc.perform(put("/update/vehicle/" + VEHICLE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(vehicleDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.name", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.name", CoreMatchers.is("Car")));
    }

    @Test
    void VehicleController_DeleteVehicleById_ReturnVehicle() throws Exception{
        doNothing().when(vehicleService).deleteVehicle(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/vehicle/" + VEHICLE_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    // get the same shi as others
}