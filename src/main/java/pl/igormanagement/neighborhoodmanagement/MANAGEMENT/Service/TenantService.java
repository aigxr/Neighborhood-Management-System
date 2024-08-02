package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.FileDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.TenantDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.File;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FileRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.TenantRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TenantService {
    private final TenantRepository tenantRepository;
    private final FileRepository fileRepository;

    public List<TenantDto> getAllTenants() {
        return tenantRepository.findAll().stream().map(TenantDtoMapper::map).toList();
    }

    public TenantDtoResponse getTenantDto(Long id) {
        TenantDtoResponse tenantResponse = tenantRepository.findById(id).map(TenantDtoMapper::response)
                .orElseThrow(() -> new NotFoundException("Tenant not found"));
        List<FileDtoResponse> allFiles = fileRepository.findAllByTenantId(id)
                .stream().map(FileDtoMapper::response).toList();
        tenantResponse.setFiles(allFiles);
        return tenantResponse;
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


    @Transactional
    public TenantDto updateTenant(Long id, TenantDto dto) {
        Tenant foundTenant = getTenant(id);
        Tenant mappedTenant = TenantDtoMapper.updateMap(foundTenant, dto);

        Tenant savedTenant = tenantRepository.save(mappedTenant);
        return TenantDtoMapper.map(savedTenant);
    }

    @Transactional
    public void deleteTenant(Long id) {
        Tenant foundTenant = getTenant(id);
        tenantRepository.deleteById(foundTenant.getId());
        fileRepository.deleteAllByTenantId(id);
    }
}
