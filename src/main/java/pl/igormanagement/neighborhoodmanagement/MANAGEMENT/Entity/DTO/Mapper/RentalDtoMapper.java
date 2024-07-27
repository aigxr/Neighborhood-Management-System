package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RentalDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Rental;

public class RentalDtoMapper {
    public static RentalDto map(Rental rental) {
        RentalDto dto = new RentalDto();
        dto.setId(rental.getId());
        dto.setTenant(rental.getTenant().getId());
        dto.setBlockId(rental.getBlock().getId());
        dto.setFlat(rental.getFlat().getId());
        dto.setParkingId(rental.getParking().getId());
        dto.setStartDate(rental.getStartDate());
        dto.setEndDate(rental.getEndDate());
        return dto;
    }
}
