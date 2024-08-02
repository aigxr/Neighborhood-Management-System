package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;

public class TenantDtoMapper {
    public static TenantDto map(Tenant tenant) {
        TenantDto dto = new TenantDto();
        dto.setId(tenant.getId());
        dto.setFirstName(tenant.getFirstName());
        dto.setLastName(tenant.getLastName());
        dto.setPesel(tenant.getPesel());
        dto.setAddress(tenant.getAddress());
        dto.setBirthDate(tenant.getBirthDate());
        return dto;
    }

    public static TenantDtoResponse response(Tenant tenant) {
        TenantDtoResponse dto = new TenantDtoResponse();
        dto.setId(tenant.getId());
        dto.setFirstName(tenant.getFirstName());
        dto.setLastName(tenant.getLastName());
        dto.setPesel(tenant.getPesel());
        dto.setAddress(tenant.getAddress());
        dto.setBirthDate(tenant.getBirthDate());
        return dto;
    }

    public static Tenant updateMap(Tenant tenant, TenantDto dto) {
        if (dto.getId() != null)
            tenant.setId(dto.getId());
        if (dto.getFirstName() != null)
            tenant.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            tenant.setLastName(dto.getLastName());
        if (dto.getPesel() != null)
            tenant.setPesel(dto.getPesel());
        if (dto.getAddress() != null)
            tenant.setAddress(dto.getAddress());
        if (dto.getBirthDate() != null)
            tenant.setBirthDate(dto.getBirthDate());
        return tenant;
    }


    public static Tenant map(TenantDto dto) {
        Tenant tenant = new Tenant();
        tenant.setFirstName(dto.getFirstName());
        tenant.setLastName(dto.getLastName());
        tenant.setPesel(dto.getPesel());
        tenant.setAddress(dto.getAddress());
        tenant.setBirthDate(dto.getBirthDate());
        return tenant;
    }
}
