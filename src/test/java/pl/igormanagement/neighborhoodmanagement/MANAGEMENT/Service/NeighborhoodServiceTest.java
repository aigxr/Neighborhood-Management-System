package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.NeighborhoodDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.BlockRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.NeighborhoodRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Slf4j
class NeighborhoodServiceTest {
    private static final Long NEIGH_ID = 1L;
    private static final Long DEVELOPER_ID = 1L;
    @Mock private NeighborhoodRepository neighborhoodRepository;
    @Mock private BlockRepository blockRepository;
    @Mock private DeveloperService developerService;
    @Captor private ArgumentCaptor<Neighborhood> captor;
    private NeighborhoodService neighborhoodService;
    private Developer developer;
    private NeighborhoodDto neighborhoodDto;
    private Neighborhood neighborhood;

    @BeforeEach
    public void init() {
        neighborhoodService = new NeighborhoodService(neighborhoodRepository, developerService, blockRepository);

        // simulation of non-existent developer
        developer = new Developer();
        developer.setId(DEVELOPER_ID);
        developer.setFirstName("Jan");
        developer.setLastName("Kowalski");

        // simulation of non-existent current neighborhood
        neighborhood = new Neighborhood();
        neighborhood.setId(NEIGH_ID);
        neighborhood.setName("SECURITY");
        neighborhood.setCity("Łódź");
        neighborhood.setDeveloper(developer); // NEEDS TO BE SET

        // simulation of data passed by user
        neighborhoodDto = new NeighborhoodDto();
        neighborhoodDto.setId(NEIGH_ID);
        neighborhoodDto.setName("SOLID");
        neighborhoodDto.setCity("Warszawa");
        neighborhoodDto.setDeveloperId(DEVELOPER_ID);
    }

    @Test
    void NeighborhoodService_GetNeighborhoodById_ReturnsDto() {

        // when
        when(neighborhoodRepository.findById(anyLong())).thenReturn(Optional.of(neighborhood));
        NeighborhoodDto actualDto = neighborhoodService.getNeighborhoodDto(NEIGH_ID);

        // then
        assertThat(actualDto).isNotNull();
        assertThat(actualDto.getId()).isEqualTo(NEIGH_ID);
        assertThat(actualDto.getName()).isEqualTo("SECURITY");
        assertThat(actualDto.getDeveloperId()).isEqualTo(developer.getId());
        assertThat(actualDto.getCity()).isEqualTo("Łódź");

    }

    @Test
    void NeighborhoodService_GetAllNeighborhoods_ReturnsListDto() {

        // when

        when(neighborhoodRepository.findAll()) // mock this also cause we use it as well
                .thenReturn(Collections.singletonList(neighborhood));

        List<NeighborhoodDto> list = neighborhoodService.getAllNeighborhoods();
        NeighborhoodDto foundNeighborhood = list.getFirst();
        // then
        assertThat(foundNeighborhood.getId()).isNotNull();
        assertThat(foundNeighborhood.getName()).isEqualTo("SECURITY");
        assertThat(foundNeighborhood.getCity()).isEqualTo("Łódź");
    }

    @Test
    void NeighborhoodService_GetAllNeighborhoodsByDeveloperId_ReturnsListDto() {

        // when
        when(developerService.getDeveloper(anyLong())).thenReturn(developer); // mock this cause we use it in service

        when(neighborhoodRepository.findAllByDeveloperId(anyLong())) // mock this also cause we use it as well
                .thenReturn(Collections.singletonList(neighborhood));

        List<NeighborhoodDto> list = neighborhoodService.getNeighborhoodsByDeveloperId(DEVELOPER_ID);
        NeighborhoodDto foundNeighborhood = list.getFirst();
        // then
        assertThat(foundNeighborhood.getId()).isNotNull();
        assertThat(foundNeighborhood.getName()).isEqualTo("SECURITY");
        assertThat(foundNeighborhood.getCity()).isEqualTo("Łódź");
    }

    @Test
    void NeighborhoodService_CreateNeighborhood_ReturnsDto() {

        // when
        when(neighborhoodRepository.findById(anyLong())).thenReturn(Optional.of(neighborhood));
        when(neighborhoodRepository.save(any(Neighborhood.class))).thenReturn(neighborhood);

        neighborhoodService.createNeighborhood(neighborhoodDto);
        verify(neighborhoodRepository, times(1)).save(any());

        NeighborhoodDto foundDto = neighborhoodService.getNeighborhoodDto(NEIGH_ID);

        // then
        assertThat(foundDto.getId()).isNotNull();
        assertThat(foundDto.getName()).isEqualTo("SECURITY");
        assertThat(foundDto.getCity()).isEqualTo("Łódź");
    }

    @Test
    void NeighborhoodService_UpdateNeighborhood_ReturnsDto() {

        // when
        when(developerService.getDeveloper(DEVELOPER_ID)).thenReturn(developer);
        when(neighborhoodRepository.findById(NEIGH_ID)).thenReturn(Optional.of(neighborhood));
        when(neighborhoodRepository.save(any(Neighborhood.class))).thenReturn(neighborhood);

        neighborhoodService.updateNeighborhood(NEIGH_ID, neighborhoodDto);
        verify(neighborhoodRepository, times(1)).save(captor.capture());

        Neighborhood foundNeighborhood = neighborhoodService.getNeighborhood(NEIGH_ID);

        // then
        assertThat(foundNeighborhood.getName()).isEqualTo("SOLID");
        assertThat(foundNeighborhood.getCity()).isEqualTo("Warszawa");
    }

    @Test
    void NeighborhoodService_DeleteById_ReturnNeighborhoodDto() {

        when(neighborhoodRepository.findById(anyLong())).thenReturn(Optional.of(neighborhood)); // returns developer by any id

        assertAll(() -> neighborhoodService.deleteNeighborhood(1L)); // asserts that the delete method was used

        verify(neighborhoodRepository).deleteById(NEIGH_ID);
        verifyNoMoreInteractions(neighborhoodRepository);
    }


    @Test
    void NeighborhoodService_Throw_ReturnNotFoundNeighborhood() {
        // when
        when(neighborhoodRepository.findById(anyLong())).thenReturn(Optional.empty()); // we can return empty because that was not found

        // then
        assertThatThrownBy(() -> neighborhoodService.getNeighborhoodDto(NEIGH_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Neighborhood");
    }

    @Test
    void NeighborhoodService_ThrowWhenGetDeveloper_ReturnNotFoundDeveloper() {
        // when
        when(developerService.getDeveloper(anyLong())).thenThrow(new NotFoundException("Developer not found"));
        // then
        assertThatThrownBy(() -> neighborhoodService.createNeighborhood(neighborhoodDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Developer");
    }

    @Test
    void NeighborhoodService_ThrowWhenUpdating_ReturnNotFoundDeveloper() {

        // when
        when(neighborhoodRepository.findById(anyLong())).thenReturn(Optional.of(neighborhood));

        when(developerService.getDeveloper(anyLong())).thenThrow(new NotFoundException("Developer not found"));

        // then
        assertThatThrownBy(() -> neighborhoodService.updateNeighborhood(NEIGH_ID, neighborhoodDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Developer");
    }

    @Test
    void NeighborhoodService_DeleteById_CheckByThrow_ReturnNotFoundNeighborhood() {

        when(neighborhoodRepository.findById(anyLong())).thenReturn(Optional.empty());

        //when
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> neighborhoodService.deleteNeighborhood(NEIGH_ID));

        // then
        assertThat(notFoundException.getMessage()).isEqualTo("Neighborhood not found");
    }

}