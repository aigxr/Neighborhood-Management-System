package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.VehicleRepository;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.Vehicle;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.VehicleDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class VehicleServiceTest {
    private static final Long VEHICLE_ID = 1L;

    @Mock
    private VehicleRepository vehicleRepository;

    private Vehicle vehicle;
    private VehicleDto vehicleDto;
    private VehicleService vehicleService;

    @BeforeEach
    void init() {
        vehicleService = new VehicleService(vehicleRepository);

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
    void VehicleService_CreateVehicle_ReturnVehicle() {
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle savedVehicle = vehicleService.createVehicle(vehicleDto);
        verify(vehicleRepository).save(any(Vehicle.class));

        assertThat(savedVehicle).isNotNull();
        assertThat(savedVehicle.getName()).isEqualTo("Car");
        assertThat(savedVehicle.getALength()).isEqualTo(2.0);
    }

    @Test
    void VehicleService_UpdateVehicleById_ReturnVehicle() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        Vehicle updatedVehicle = vehicleService.updateVehicle(VEHICLE_ID, vehicleDto);
        verify(vehicleRepository).save(any(Vehicle.class));

        assertThat(updatedVehicle).isNotNull();
        assertThat(updatedVehicle.getName()).isEqualTo("Vehicle");
        assertThat(updatedVehicle.getALength()).isEqualTo(3.0);
    }

    @Test
    void VehicleService_DeleteVehicleById_ReturnNothing() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));

        vehicleService.deleteVehicle(VEHICLE_ID);
        verify(vehicleRepository).deleteById(anyLong());
        verifyNoMoreInteractions(vehicleRepository);
    }

    @Test
    void VehicleService_GetVehicleById_ReturnVehicle() {
        when(vehicleRepository.findById(anyLong())).thenReturn(Optional.of(vehicle));

        Vehicle foundVehicle = vehicleService.getVehicle(VEHICLE_ID);
        verify(vehicleRepository).findById(anyLong());

        assertThat(foundVehicle).isNotNull();
        assertThat(foundVehicle.getName()).isEqualTo("Car");
        assertThat(foundVehicle.getALength()).isEqualTo(2.0);
    }

    @Test
    void VehicleService_GetAllVehicles_ReturnListVehicle() {
        when(vehicleRepository.findAll()).thenReturn(Collections.singletonList(vehicle));

        List<Vehicle> list = vehicleService.getAllVehicles();
        verify(vehicleRepository).findAll();

        Vehicle firstVehicle = list.get(0);
        assertThat(firstVehicle).isNotNull();
        assertThat(firstVehicle.getName()).isEqualTo("Car");
        assertThat(firstVehicle.getALength()).isEqualTo(2.0);
    }
}