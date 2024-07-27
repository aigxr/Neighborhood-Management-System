package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.TenantService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TenantController {
    private final TenantService tenantService;

    @GetMapping("/tenants")
    public ResponseEntity<List<TenantDto>> getAllTenants() {
        return ResponseEntity.ok(tenantService.getAllTenants());
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<TenantDto> getTenantDtoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tenantService.getTenantDto(id));
    }
}
