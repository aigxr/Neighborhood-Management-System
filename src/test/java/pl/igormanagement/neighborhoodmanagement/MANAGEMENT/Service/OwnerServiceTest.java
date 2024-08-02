package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FlatRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.OwnerRepository;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    private static final Long OWNER_ID = 1L;

    @Mock private OwnerRepository ownerRepository;
    @Mock private FlatRepository flatRepository;

    private OwnerService ownerService;
    private FlatDtoResponse flatDtoResponse;
    private Owner owner;
    private OwnerDto ownerDto;
    private OwnerDtoResponse ownerDtoResponse;

    @BeforeEach
    void init() {
        ownerService = new OwnerService(ownerRepository, flatRepository);
        // DATA SAVED TO REPO
        owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setFirstName("John");
        owner.setLastName("Kowalski");

        // FULL DATA PASSED BY USER
        ownerDto = new OwnerDto();
        ownerDto.setFirstName("Igor");
        ownerDto.setLastName("Nowak");
        ownerDto.setPesel(12341235341L);
        ownerDto.setAddress("Some street");
        ownerDto.setBirthDate(LocalDate.of(2000, 2, 2));
    }

    @Test
    void OwnerService_CreateOwner_ReturnOwnerDto() {
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        OwnerDto savedOwner = ownerService.createOwner(ownerDto);
        verify(ownerRepository).save(any(Owner.class));

        assertThat(savedOwner.getFirstName()).isEqualTo("John");
        assertThat(savedOwner.getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void OwnerService_UpdateOwnerById_ReturnOwnerDto() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

        OwnerDto savedOwner = ownerService.updateOwner(OWNER_ID, ownerDto);
        verify(ownerRepository).save(any(Owner.class));

        assertThat(savedOwner.getFirstName()).isEqualTo("Igor");
        assertThat(savedOwner.getLastName()).isEqualTo("Nowak"); // changed data
    }

    @Test
    void OwnerService_DeleteOwnerById_ReturnNothing() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        ownerService.deleteOwner(OWNER_ID);
        verify(ownerRepository).deleteById(anyLong());
        verifyNoMoreInteractions(ownerRepository);
    }

    @Test
    void OwnerService_GetOwnerDtoById_ReturnOwnerDto() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner));

        OwnerDto foundOwner = ownerService.getOwnerDto(OWNER_ID);
        verify(ownerRepository).findById(anyLong());

        assertThat(foundOwner.getFirstName()).isEqualTo("John");
        assertThat(foundOwner.getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void OwnerService_GetAllOwners_ReturnListOwnerDto() {
        flatDtoResponse = new FlatDtoResponse();
        flatDtoResponse.setName("Apartment");
        List<FlatDtoResponse> flats = new ArrayList<>();
        flats.add(flatDtoResponse);

        // DATA RECEIVED BY CLIENT
        ownerDtoResponse = new OwnerDtoResponse();
        ownerDtoResponse.setId(OWNER_ID);
        ownerDtoResponse.setFirstName("John");
        ownerDtoResponse.setLastName("Kowalski");
        ownerDtoResponse.setFlats(flats);

        when(ownerRepository.findAll()).thenReturn(Collections.singletonList(owner));

        List<OwnerDto> foundList = ownerService.getAllOwners();
        verify(ownerRepository).findAll();

        OwnerDto firstOwner = foundList.get(0);
        assertThat(firstOwner.getFirstName()).isEqualTo("John");
        assertThat(firstOwner.getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void OwnerService_ThrowWhenUpdate_NotFoundException() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.updateOwner(OWNER_ID, ownerDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Owner not");
    }

    @Test
    void OwnerService_ThrowWhenDelete_NotFoundException() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> ownerService.deleteOwner(OWNER_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Owner not");
    }
}