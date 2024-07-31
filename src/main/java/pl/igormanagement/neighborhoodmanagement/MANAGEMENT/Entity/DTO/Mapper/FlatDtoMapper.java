package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;

public class FlatDtoMapper {
    public static FlatDto map(Flat flat) {
        FlatDto dto = new FlatDto();
        dto.setId(flat.getId());
        dto.setBlockId(flat.getBlock().getId());
        dto.setOwnerId(flat.getOwner().getId());
        dto.setTenantId(flat.getTenant().getId());
        return dto;
    }

//    public static Flat map(FlatDto dto) {
//        Flat flat = new Flat();
//        return flat;
//    }

    public static FlatDtoResponse response(Flat flat) {
        FlatDtoResponse dto = new FlatDtoResponse();
        dto.setId(flat.getId());
        dto.setName(flat.getName());
        dto.setBlockId(flat.getBlock().getId());
        dto.setOwnerId(flat.getOwner().getId());
        if (flat.getTenant() != null)
            dto.setTenantId(flat.getTenant().getId());
        dto.setRoom(flat.getRoom());
        return dto;
    }

//    public static Flat map(Flat flat,
//                                   FlatDto dto) {
//        if (dto.getName() != null)
//            flat.setName(dto.getName());
//        if (dto.getALength() != null)
//            flat.setRoom(dto.getCity());
//        if (dto.getBLength() != null)
//            neighborhood.setAddress(dto.getAddress());
//        return neighborhood;
//    }
}
