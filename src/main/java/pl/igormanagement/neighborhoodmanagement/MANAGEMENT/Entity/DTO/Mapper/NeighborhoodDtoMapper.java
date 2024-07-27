package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.DeveloperService;

import java.util.List;

public class NeighborhoodDtoMapper {
    public static NeighborhoodDto map(Neighborhood neighborhood) {
        NeighborhoodDto dto = new NeighborhoodDto();
        dto.setId(neighborhood.getId());
        dto.setDeveloperId(neighborhood.getDeveloper().getId());
        dto.setName(neighborhood.getName());
        dto.setCity(neighborhood.getCity());
        dto.setAddress(neighborhood.getAddress());
        return dto;
    }

    public static Neighborhood map(Neighborhood neighborhood,
                                   NeighborhoodDto dto,
                                   Developer developer) {
//        neighborhood.setId(neighborhood.getId());
        if (dto.getDeveloperId() != null)
            neighborhood.setDeveloper(developer);
        if (dto.getName() != null)
            neighborhood.setName(dto.getName());
        if (dto.getCity() != null)
            neighborhood.setCity(dto.getCity());
        if (dto.getAddress() != null)
            neighborhood.setAddress(dto.getAddress());
        return neighborhood;
    }
}
