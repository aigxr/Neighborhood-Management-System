package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDtoResponse;
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
    public ResponseEntity<TenantDtoResponse> getTenantDtoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(tenantService.getTenantDto(id));
    }

    @PostMapping("/create/tenant")
    public ResponseEntity<?> createTenant(@Valid @RequestBody TenantDto dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        TenantDto savedTenant = tenantService.createTenant(dto);
        return new ResponseEntity<>(savedTenant, HttpStatus.CREATED);
    }

    @PutMapping("/update/tenant/{id}")
    public ResponseEntity<?> createTenant(@PathVariable("id") Long id,
                                          @Valid @RequestBody TenantDto dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        TenantDto updatedTenant = tenantService.updateTenant(id, dto);
        return ResponseEntity.ok(updatedTenant);
    }

    @DeleteMapping("/delete/tenant/{id}")
    public void deleteTenant(@PathVariable("id") Long id) {
        tenantService.deleteTenant(id);
    }

}
