package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RoomDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.ParkingRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParkingServiceTest {
    private static final Long PARKING_ID = 1L;
    @Mock
    private ParkingRepository parkingRepository;

    @Mock
    private RoomService roomService;

    private ParkingService parkingService;
    private Parking parking;
    private ParkingDto parkingDto;
    private Room room;
    @BeforeEach
    void init() {
        parkingService = new ParkingService(parkingRepository, roomService);

        // data passed
        parkingDto = new ParkingDto();
        parkingDto.setId(PARKING_ID);
        parkingDto.setName("A");
        parkingDto.setALength(3.0);
        parkingDto.setBLength(1.5);

        room = new Room();
        room.setALength(parkingDto.getALength());
        room.setBLength(parkingDto.getBLength());

        parking = new Parking();
        parking.setId(PARKING_ID);
        parking.setName("A");
        parking.setRoom(room);
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
                .hasMessageStartingWith("Parking not");
    }

    @Test
    void ParkingService_ThrowWhenDelete_NotFoundException() {
        when(parkingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> parkingService.deleteParking(PARKING_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Parking not");
    }
}