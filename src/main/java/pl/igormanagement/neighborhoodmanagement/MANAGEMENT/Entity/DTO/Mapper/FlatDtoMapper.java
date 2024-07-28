package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;

public class FlatDtoMapper {
    public static FlatDto map(Flat flat) {
        FlatDto dto = new FlatDto();
        dto.setId(flat.getId());
        dto.setBlockId(flat.getBlock().getId());
        dto.setOwnerId(flat.getOwner().getId());
        return dto;
    }

    public static Flat map(FlatDto dto) {
        Flat flat = new Flat();
        flat.setALength(dto.getALength());
        flat.setBLength(dto.getBLength());
        return flat;
    }
}
