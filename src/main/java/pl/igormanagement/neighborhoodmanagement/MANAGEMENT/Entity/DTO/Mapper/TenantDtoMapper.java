package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;

public class TenantDtoMapper {
    public static TenantDto map(Tenant tenant) {
        TenantDto dto = new TenantDto();
        dto.setId(tenant.getId());
        dto.setFirstName(tenant.getFirstName());
        dto.setLastName(tenant.getLastName());
        dto.setPESEL(tenant.getPESEL());
        dto.setAddress(tenant.getAddress());
        dto.setBirthDate(tenant.getBirthDate());
        return dto;
    }

    public static Tenant map(TenantDto dto) {
        Tenant tenant = new Tenant();
        tenant.setFirstName(dto.getFirstName());
        tenant.setLastName(dto.getLastName());
        tenant.setPESEL(dto.getPESEL());
        tenant.setAddress(dto.getAddress());
        tenant.setBirthDate(dto.getBirthDate());
        return tenant;
    }
}
