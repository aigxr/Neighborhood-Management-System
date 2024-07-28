package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.TenantDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.TenantRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;

    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll().stream().map(TenantDtoMapper::map).toList();
    }

    public TenantDto getTenantDto(Long id) {
        return tenantRepository.findById(id).map(TenantDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

    public Tenant getTenant(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
    }

    @Transactional
    public TenantDto createTenant(TenantDto dto) {
        Tenant savedTenant = tenantRepository.save(TenantDtoMapper.map(dto));
        return TenantDtoMapper.map(savedTenant);
    }
}
