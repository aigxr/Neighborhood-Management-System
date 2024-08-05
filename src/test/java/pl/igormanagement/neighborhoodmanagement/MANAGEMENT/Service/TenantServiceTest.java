package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FileRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.TenantRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TenantServiceTest {
    private static final Long TENANT_ID = 1L;

    @Mock private TenantRepository tenantRepository;
    @Mock private FileRepository fileRepository;

    private TenantService tenantService;
    private Tenant tenant;
    private TenantDto tenantDto;
    private TenantDtoResponse tenantDtoResponse;

    @BeforeEach
    void init() {
        tenantService = new TenantService(tenantRepository, fileRepository);

        tenant = new Tenant();
        tenant.setId(TENANT_ID);
        tenant.setFirstName("Igor");
        tenant.setLastName("Nowak");

        tenantDto = new TenantDto();
        tenantDto.setFirstName("Igor");
        tenantDto.setLastName("Nowak");
        tenantDto.setPesel(12341235341L);
        tenantDto.setAddress("Some street");
        tenantDto.setBirthDate(LocalDate.of(2000, 2, 2));

        tenantDtoResponse = new TenantDtoResponse();
        tenantDtoResponse.setFirstName("Igor");
        tenantDtoResponse.setLastName("Nowak");
        tenantDtoResponse.setPesel(12341235341L);
        tenantDtoResponse.setAddress("Some street");
        tenantDtoResponse.setBirthDate(LocalDate.of(2000, 2, 2));
    }

    @Test
    void TenantService_CreateTenant_ReturnTenantDtoResponse() {
        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);

        TenantDto foundTenant = tenantService.createTenant(tenantDto);
        verify(tenantRepository).save(any(Tenant.class));

        assertThat(foundTenant.getFirstName()).isEqualTo("Igor");
        assertThat(foundTenant.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void TenantService_UpdateTenantById_ReturnTenantDto() {
        when(tenantRepository.findById(anyLong())).thenReturn(Optional.of(tenant));
        when(tenantRepository.save(any(Tenant.class))).thenReturn(tenant);

        TenantDto savedTenant = tenantService.updateTenant(TENANT_ID, tenantDto);
        verify(tenantRepository).save(any(Tenant.class));

        assertThat(savedTenant.getFirstName()).isEqualTo("Igor");
        assertThat(savedTenant.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void TenantService_DeleteTenantById_ReturnNothing() {
        when(tenantRepository.findById(anyLong())).thenReturn(Optional.of(tenant));

        tenantService.deleteTenant(TENANT_ID);
        verify(tenantRepository).deleteById(anyLong());
        verifyNoMoreInteractions(tenantRepository);
    }

    @Test
    void TenantService_GetTenantDtoResponse_ReturnTenantDtoResponse() {
        when(tenantRepository.findById(anyLong())).thenReturn(Optional.of(tenant));

        TenantDtoResponse foundTenant = tenantService.getTenantDto(TENANT_ID);
        verify(tenantRepository).findById(anyLong());

        assertThat(foundTenant.getFirstName()).isEqualTo("Igor");
        assertThat(foundTenant.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void TenantService_GetAllTenantsDto_ReturnTenantDto() {
        when(tenantRepository.findAll()).thenReturn(Collections.singletonList(tenant));

        List<TenantDto> listOfPeople = tenantService.getAllTenants();
        verify(tenantRepository).findAll();

        TenantDto foundTenant = listOfPeople.get(0);
        assertThat(foundTenant.getFirstName()).isEqualTo("Igor");
        assertThat(foundTenant.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void TenantService_ThrowWhenUpdate_NotFoundException() {
        when(tenantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tenantService.updateTenant(TENANT_ID, tenantDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Tenant not");
    }

    @Test
    void TenantService_ThrowWhenDelete_NotFoundException() {
        when(tenantRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tenantService.deleteTenant(TENANT_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Tenant not");
    }
}