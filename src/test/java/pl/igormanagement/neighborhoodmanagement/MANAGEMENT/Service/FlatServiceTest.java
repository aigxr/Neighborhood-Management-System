package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RoomDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FlatRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class FlatServiceTest {
    private static final Long FLAT_ID = 1L;
    private static final Long OWNER_ID = 1L;
    private static final Long BLOCK_ID = 1L;
    private static final Long TENANT_ID = 1L;


    @Mock private FlatRepository flatRepository;
    @Mock private OwnerService ownerService;
    @Mock private BlockService blockService;
    @Mock private TenantService tenantService;
    @Mock private RoomService roomService;

    private FlatService flatService;
    private Room room;
    private Owner owner;
    private Block block;
    private Tenant tenant;
    private Flat flat;
    private FlatDto flatDto;

    @BeforeEach
    void init() {
        flatService = new FlatService(flatRepository, ownerService, blockService, tenantService, roomService);
        // all simulation is defined here
        room = new Room();
        room.setId(1L);
        room.setALength(30.0);
        room.setBLength(40.0);

        owner = new Owner();
        owner.setId(OWNER_ID);
        owner.setFirstName("John");

        block = new Block();
        block.setId(BLOCK_ID);
        block.setName("Block Name");

        tenant = new Tenant();
        tenant.setId(TENANT_ID);
        tenant.setFirstName("John");

        flat = new Flat();
        flat.setId(FLAT_ID);
        flat.setName("House");
        flat.setOwner(owner);
        flat.setBlock(block);
        flat.setTenant(tenant);
        flat.setRoom(room);

        // data to change / create a new flat
        flatDto = new FlatDto();
        flatDto.setId(FLAT_ID);
        flatDto.setName("Apartment");
        // data for creating a new room
        flatDto.setALength(30.0);
        flatDto.setBLength(40.0);
        flatDto.setOwnerId(OWNER_ID);
        flatDto.setBlockId(BLOCK_ID);
        flatDto.setTenantId(TENANT_ID);
    }

    @Test
    void FlatService_CreateFlatWithRoom_ReturnFlatResponseDto() {

        // when
        when(roomService.createRoom(any(FlatDto.class))).thenReturn(room); // if any room is created return room
        when(ownerService.getOwner(anyLong())).thenReturn(owner); // any owner called return owner
        when(blockService.getBlock(anyLong())).thenReturn(block); // any block called return block
        when(tenantService.getTenant(anyLong())).thenReturn(null); // any tenant called return tenant

        when(flatRepository.save(any(Flat.class))).thenReturn(flat); // any FLAT to be saved returns flat

        // then
        FlatDtoResponse savedFlat = flatService.createFlat(flatDto);
//        verify(flatRepository.save(captor.capture()));

        assertThat(savedFlat).isNotNull();
        assertThat(savedFlat.getName()).isEqualTo("House");
        assertThat(savedFlat.getTenantId()).isEqualTo(TENANT_ID);

        assertThat(savedFlat.getRoom()).isNotNull();
    }

    @Test
    void FlatService_GetAllFlats_ReturnListResponseDto() {
        // when

        when(flatRepository.findAll()).thenReturn(Collections.singletonList(flat)); // we return every time flat

        List<FlatDtoResponse> foundFlats = flatService.getAllFlats();// so this gon return dto of FLAT.class object
        verify(flatRepository).findAll();

        // then
        FlatDtoResponse foundFlat = foundFlats.getFirst();

        assertThat(foundFlat.getId()).isEqualTo(FLAT_ID);
        assertThat(foundFlat.getName()).isEqualTo("House");
        assertThat(foundFlat.getRoom()).isEqualTo(room);

    }

    @Test
    void FlatService_GetFlatById_ReturnResponseDto() {
        // when

        when(flatRepository.findById(anyLong())).thenReturn(Optional.of(flat)); // we return every time flat

        FlatDtoResponse foundFlat = flatService.getFlatDto(FLAT_ID); // so this gon return dto of FLAT.class object
        verify(flatRepository).findById(anyLong());

        // then

        assertThat(foundFlat.getId()).isEqualTo(FLAT_ID);
        assertThat(foundFlat.getName()).isEqualTo("House");
        assertThat(foundFlat.getRoom()).isEqualTo(room);
    }

    @Test
    void FlatService_UpdateFlatById_ReturnResponseDto() {
        // when
        // flat found that'll be changed then
        when(flatRepository.findById(anyLong())).thenReturn(Optional.of(flat));

        // simulation of getting objects and returning created ones
        when(ownerService.getOwner(anyLong())).thenReturn(owner);
        when(blockService.getBlock(anyLong())).thenReturn(block);
        when(tenantService.getTenant(anyLong())).thenReturn(null);

        when(flatRepository.save(any(Flat.class))).thenReturn(flat);


        FlatDtoResponse updatedFlat = flatService.updateFlat(FLAT_ID, flatDto);

        // then
        assertThat(updatedFlat).isNotNull();
        assertThat(updatedFlat.getId()).isNotNull();
        assertThat(updatedFlat.getName()).isEqualTo("Apartment"); // it's equal to apartment because we saved DTO
        assertThat(updatedFlat.getOwnerId()).isEqualTo(OWNER_ID);
        assertThat(updatedFlat.getBlockId()).isEqualTo(BLOCK_ID);
        assertThat(updatedFlat.getRoom()).isNotNull();
    }

    @Test
    void FlatService_DeleteFlatById_ReturnNothing() {
        // when
//        when(roomService.getRoom())
        when(flatRepository.findById(anyLong())).thenReturn(Optional.of(flat));

        flatService.deleteFlat(FLAT_ID);
        verify(flatRepository, times(1)).deleteById(FLAT_ID);
        verifyNoMoreInteractions(flatRepository);
    }

    @Test
    void FlatService_CreateFlat_ThrowsOwnerNotFound() {
        // when
        when(ownerService.getOwner(anyLong())).thenThrow(new NotFoundException("Owner not found"));

        assertThatThrownBy(() -> flatService.createFlat(flatDto))
                .hasMessage("Owner not found")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void FlatService_CreateFlat_ThrowsBlockNotFound() {
        // when
        when(blockService.getBlock(anyLong())).thenThrow(new NotFoundException("Block not found"));

        assertThatThrownBy(() -> flatService.createFlat(flatDto))
                .hasMessage("Block not found")
                .isInstanceOf(NotFoundException.class);
    }


    @Test
    void FlatService_UpdateFlat_ThrowsOwnerNotFound() {
        // when
        when(flatRepository.findById(anyLong())).thenReturn(Optional.of(flat));
        when(ownerService.getOwner(anyLong())).thenThrow(new NotFoundException("Owner not found"));

        assertThatThrownBy(() -> flatService.updateFlat(FLAT_ID, flatDto))
                .hasMessage("Owner not found")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void FlatService_UpdateFlat_ThrowsBlockNotFound() {
        // when
        when(flatRepository.findById(anyLong())).thenReturn(Optional.of(flat));
        when(blockService.getBlock(anyLong())).thenThrow(new NotFoundException("Block not found"));

        assertThatThrownBy(() -> flatService.updateFlat(FLAT_ID, flatDto))
                .hasMessage("Block not found")
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void FlatService_DeleteById_ThrowsFlatNotFound() {
        // when
        when(flatRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flatService.deleteFlat(FLAT_ID))
                .hasMessageStartingWith("Flat")
                .isInstanceOf(NotFoundException.class);

        verify(flatRepository, times(0)).deleteById(FLAT_ID);
        verifyNoMoreInteractions(flatRepository);
    }
}