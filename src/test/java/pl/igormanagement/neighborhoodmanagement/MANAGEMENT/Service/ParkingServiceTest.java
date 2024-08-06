package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.AlreadyExistsException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RoomDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.ParkingRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.VehicleRepository;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.Vehicle;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {
    private static final Long PARKING_ID = 1L;
    private static final Long VEHICLE_ID = 1L;
    @Mock
    private ParkingRepository parkingRepository;
    @Mock
    private VehicleService vehicleService;

    @Mock
    private RoomService roomService;

    private ParkingService parkingService;
    private Parking parking;
    private ParkingDto parkingDto;
    private Room room;
    private Vehicle vehicle;
    @BeforeEach
    void init() {
        parkingService = new ParkingService(parkingRepository, roomService, vehicleService);

        // data passed
        parkingDto = new ParkingDto();
        parkingDto.setId(PARKING_ID);
        parkingDto.setName("A");
        parkingDto.setALength(3.0);
        parkingDto.setBLength(1.5);

        room = new Room();
        room.setALength(parkingDto.getALength());
        room.setBLength(parkingDto.getBLength());
        room.setRoomArea(4.5);

        parking = new Parking();
        parking.setId(PARKING_ID);
        parking.setIsRented(true);
        parking.setName("A");
        parking.setRoom(room);

        vehicle = new Vehicle();
        vehicle.setId(VEHICLE_ID);
        vehicle.setName("car");
        vehicle.setALength(2.0);
        vehicle.setBLength(1.2);
    }

    @Test
    void ParkingService_CreateParking_ReturnParkingDto() {
        when(roomService.createRoom(any(ParkingDto.class))).thenReturn(room);
        when(parkingRepository.save(any(Parking.class))).thenReturn(parking);

        ParkingDtoResponse createdParking = parkingService.createParking(parkingDto);
        verify(parkingRepository).save(any(Parking.class));
        verify(roomService).createRoom(parkingDto);

        assertThat(createdParking.getName()).isEqualTo("A");
        assertThat(createdParking.getRoom()).isNotNull();
        assertThat(createdParking.getRoom().getALength()).isEqualTo(3.0);
    }

    @Test
    void ParkingService_UpdateParkingById_ReturnParkingDto() {

        when(parkingRepository.findById(anyLong())).thenReturn(Optional.of(parking));
//        when(roomService.getRoom(anyLong())).thenReturn(room);
        when(parkingRepository.save(any(Parking.class))).thenReturn(parking);

        ParkingDtoResponse updatedParking = parkingService.updateParking(PARKING_ID, parkingDto);

        verify(parkingRepository).save(any(Parking.class));

        assertThat(updatedParking.getName()).isEqualTo("A");
        assertThat(updatedParking.getRoom()).isNotNull();
        assertThat(updatedParking.getRoom().getALength()).isEqualTo(3.0);
    }

    @Test
    void ParkingService_DeleteParkingById_ReturnNothing() {

        when(parkingRepository.findById(anyLong())).thenReturn(Optional.of(parking));

        parkingService.deleteParking(PARKING_ID);
        verify(parkingRepository).deleteById(anyLong());
        verifyNoMoreInteractions(parkingRepository);
    }

    @Test
    void ParkingService_ThrowWhenUpdate_NotFoundException() {
        when(parkingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parkingService.updateParking(PARKING_ID, parkingDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Parking space not");
    }

    @Test
    void ParkingService_ThrowWhenDelete_NotFoundException() {
        when(parkingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parkingService.deleteParking(PARKING_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Parking space not");
    }

    @Test
    void ParkingService_AddVehicleToParkingSpot_ReturnParkingDtoResponse() {
        parking.setVehicle(null);

        when(parkingRepository.save(any(Parking.class))).thenReturn(parking);
        when(parkingRepository.findById(anyLong())).thenReturn(Optional.of(parking));
        when(vehicleService.getVehicle(anyLong())).thenReturn(vehicle);

        ParkingDtoResponse savedParking = parkingService.addVehicle(PARKING_ID, VEHICLE_ID);

        assertThat(savedParking.getVehicle()).isNotNull();
        assertThat(savedParking.getVehicle().getName()).isEqualTo("car");
        assertThat(savedParking.getRoom().getRoomArea()).isEqualTo(2.1);
        assertThat(savedParking.getIsRented()).isTrue();
    }

    @Test
    void ParkingService_ThrowWhenAddVehicleToParkingSpot_ReturnParkingDtoResponse() {
        when(parkingRepository.findById(anyLong())).thenReturn(Optional.of(parking));
        parking.setVehicle(vehicle);

        assertThatThrownBy(() -> parkingService.addVehicle(PARKING_ID, VEHICLE_ID))
                .isInstanceOf(AlreadyExistsException.class)
                .hasMessageContaining("currently assigned");
    }

    @Test
    void ParkingService_RemoveVehicleFromParkingSpot_ReturnParkingDtoResponse() {
        parking.setVehicle(vehicle);

        when(parkingRepository.save(any(Parking.class))).thenReturn(parking);
        when(parkingRepository.findById(anyLong())).thenReturn(Optional.of(parking));

        ParkingDtoResponse savedParking = parkingService.removeVehicle(PARKING_ID);

        assertThat(savedParking.getVehicle()).isNull();
        assertThat(savedParking.getRoom().getRoomArea()).isEqualTo(4.5);
    }

}