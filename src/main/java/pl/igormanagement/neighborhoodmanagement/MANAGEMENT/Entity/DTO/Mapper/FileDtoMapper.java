package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import org.springframework.stereotype.Component;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.File;

public class FileDtoMapper {
    public static FileDto map(File file) {
        FileDto dto = new FileDto();
        dto.setId(file.getId());
        dto.setTitle(file.getTitle());
        dto.setDocument(file.getDocument());
        dto.setTenantId(file.getTenant().getId());
        return dto;
    }
}
