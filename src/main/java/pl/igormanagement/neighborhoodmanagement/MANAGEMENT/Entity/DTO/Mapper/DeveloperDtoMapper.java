package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;

import java.util.ArrayList;
import java.util.List;

public class DeveloperDtoMapper {
    public static DeveloperDto map(Developer developer) {
        DeveloperDto dto = new DeveloperDto();
        dto.setId(developer.getId());
        dto.setFirstName(developer.getFirstName());
        dto.setLastName(developer.getLastName());
        dto.setPesel(developer.getPesel());
        dto.setAddress(developer.getAddress());
        dto.setBirthDate(developer.getBirthDate());
        return dto;
    }

    public static Developer updateMap(Developer developer, DeveloperDto dto) {
        if (dto.getFirstName() != null)
            developer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            developer.setLastName(dto.getLastName());
        if (dto.getPesel() != null)
            developer.setPesel(dto.getPesel());
        if (dto.getAddress() != null)
            developer.setAddress(dto.getAddress());
        if (dto.getBirthDate() != null)
            developer.setBirthDate(dto.getBirthDate());
        return developer;
    }

    public static Developer map(DeveloperDto dto) {
        Developer developer = new Developer();
        developer.setFirstName(dto.getFirstName());
        developer.setLastName(dto.getLastName());
        developer.setPesel(dto.getPesel());
        developer.setAddress(dto.getAddress());
        developer.setBirthDate(dto.getBirthDate());
        return developer;
    }
}
