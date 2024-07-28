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
        dto.setPESEL(developer.getPESEL());
        dto.setAddress(developer.getAddress());
        dto.setBirthDate(developer.getBirthDate());
        return dto;
    }

    public static Developer map(DeveloperDto dto) {
        Developer developer = new Developer();
        developer.setFirstName(dto.getFirstName());
        developer.setLastName(dto.getLastName());
        developer.setPESEL(dto.getPESEL());
        developer.setAddress(dto.getAddress());
        developer.setBirthDate(dto.getBirthDate());
        return developer;
    }
}
