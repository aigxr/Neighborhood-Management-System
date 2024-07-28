package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;

public class ParkingDtoMapper {
    public static ParkingDto map(Parking parking) {
        ParkingDto dto = new ParkingDto();
        dto.setId(parking.getId());
        dto.setName(parking.getName());
        dto.setALength(parking.getALength());
        dto.setBLength(parking.getBLength());
        return dto;
    }
}
