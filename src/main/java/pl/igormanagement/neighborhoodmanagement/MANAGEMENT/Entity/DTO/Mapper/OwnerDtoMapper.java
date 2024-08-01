package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;

public class OwnerDtoMapper {
    public static OwnerDto map(Owner owner) {
        OwnerDto dto = new OwnerDto();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setPesel(owner.getPesel());
        dto.setAddress(owner.getAddress());
        dto.setBirthDate(owner.getBirthDate());
        return dto;
    }
    public static OwnerDtoResponse response(Owner owner) {
        OwnerDtoResponse dto = new OwnerDtoResponse();
        dto.setId(owner.getId());
        dto.setFirstName(owner.getFirstName());
        dto.setLastName(owner.getLastName());
        dto.setPesel(owner.getPesel());
        dto.setAddress(owner.getAddress());
        dto.setBirthDate(owner.getBirthDate());
        return dto;
    }

    public static Owner map(OwnerDto dto) {
        Owner owner = new Owner();
        owner.setFirstName(dto.getFirstName());
        owner.setLastName(dto.getLastName());
        owner.setPesel(dto.getPesel());
        owner.setAddress(dto.getAddress());
        owner.setBirthDate(dto.getBirthDate());
        return owner;
    }

    public static Owner updateMap(Owner owner, OwnerDto dto) {
        if (dto.getFirstName() != null)
            owner.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            owner.setLastName(dto.getLastName());
        if (dto.getPesel() != null)
            owner.setPesel(dto.getPesel());
        if (dto.getAddress() != null)
            owner.setAddress(dto.getAddress());
        if (dto.getBirthDate() != null)
            owner.setBirthDate(dto.getBirthDate());
        return owner;
    }
}
