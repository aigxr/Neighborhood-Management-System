package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;

public class OwnerDtoMapper {
    public static OwnerDto map(Owner owner) {
        OwnerDto dto = new OwnerDto();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setPESEL(owner.getPESEL());
        dto.setAddress(owner.getAddress());
        dto.setBirthDate(owner.getBirthDate());
        return dto;
    }

    public static Owner map(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setPESEL(dto.getPESEL());
        owner.setAddress(dto.getAddress());
        owner.setBirthDate(dto.getBirthDate());
        return owner;
    }
}
