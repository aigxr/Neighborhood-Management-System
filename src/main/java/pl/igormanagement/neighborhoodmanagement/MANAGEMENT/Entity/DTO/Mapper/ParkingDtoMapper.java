package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;

public class ParkingDtoMapper {
    public static ParkingDto map(Parking parking) {
        ParkingDto dto = new ParkingDto();
        dto.setId(parking.getId());
        dto.setName(parking.getName());
        dto.setIsRented(parking.getIsRented());
        dto.setVehicleId(parking.getVehicle().getId());
        return dto;
    }

    public static ParkingDtoResponse response(Parking parking) {
        ParkingDtoResponse dto = new ParkingDtoResponse();
        dto.setId(parking.getId());
        dto.setName(parking.getName());
        dto.setRoom(parking.getRoom());
        dto.setIsRented(parking.getIsRented());
        dto.setVehicle(parking.getVehicle());
        return dto;
    }
}
