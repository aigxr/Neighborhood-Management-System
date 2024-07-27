package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jdk.jfr.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.web.bind.annotation.Mapping;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.DeveloperDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.DeveloperRepository;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class DeveloperServiceTest {
    private static final Long DEVELOPER_ID = 1L;
    @Mock private DeveloperRepository developerRepository;
    @Captor private ArgumentCaptor<Developer> captor; // there is only Developer cause only entity can be saved to database
    private DeveloperService developerService;
    private Developer developer;
    private DeveloperDto developerDto; // we do not need to mock these classes

    @BeforeEach
    void init() {
        developerService = new DeveloperService(developerRepository);
        developer = new Developer();
        // given
        developer.setId(DEVELOPER_ID);
        developer.setFirstName("Jan");
        developer.setLastName("Kowalski");
        developerDto = new DeveloperDto();
        // simulation of real data passed by a user
        developerDto.setId(DEVELOPER_ID);
        developerDto.setFirstName("Igor");
        developerDto.setLastName("Siotor");
    }

    @Test
    void DeveloperService_CreateDeveloper_ReturnCorrectDto() {
        // simulation of what repository need to return when save() called
        developer.setId(1L); // manually set id

        // when
        // if any developer is being saved return developer we made before
        when(developerRepository.save(any(Developer.class))).thenReturn(developer);

        // add simulation data to simulation repository database
        DeveloperDto savedDeveloper = developerService.createDeveloper(developerDto);
        // check if the method was used
        verify(developerRepository, Mockito.times(1)).save(captor.capture());

        // then
        assertThat(savedDeveloper.getFirstName()).isEqualTo("Jan");
        assertThat(savedDeveloper.getId()).isNotNull();
    }

    @Test
    void DeveloperService_GetById_ReturnsDeveloperDto() {

        when(developerRepository.findById(anyLong())).thenReturn(Optional.of(developer));
        // we return optional of developer because we don't know yet if the developer exists

        DeveloperDto developerDto = DeveloperDtoMapper.map(developer); // mapped dev to dto

        DeveloperDto actualDto = developerService.getDeveloperDto(1L); // returned from repository 1L

        // then
        assertThat(actualDto).isNotNull();
        assertThat(developerDto.getFirstName()).isEqualTo(actualDto.getFirstName());
    }

    @Test
    void DeveloperService_GetAllDevelopers_ReturnsDeveloperDtoList() {
        // when
        when(developerRepository.findAll())
                .thenReturn(Collections.singletonList(developer));

        List<DeveloperDto> list = developerService.getAllDevelopers();
        DeveloperDto foundDeveloper = list.getFirst();
        // then
        assertThat(foundDeveloper.getId()).isNotNull();
        assertThat(foundDeveloper.getFirstName()).isEqualTo("Jan");
        assertThat(foundDeveloper.getLastName()).isEqualTo("Kowalski");
    }

    @Test
    void DeveloperService_UpdateDeveloper_ReturnDeveloperDto() {

        when(developerRepository.findById(DEVELOPER_ID)).thenReturn(Optional.of(developer));
        when(developerRepository.save(any(Developer.class))).thenReturn(developer); // ALWAYS AND ALWAYS NEEDS TO BE
//        when(developerService.getDeveloper(anyLong())).thenReturn(developer);

        developerService.updateDeveloper(DEVELOPER_ID, developerDto);
        verify(developerRepository, times(1)).save(captor.capture());

        Developer foundDeveloper = developerService.getDeveloper(DEVELOPER_ID);

        assertThat(foundDeveloper.getFirstName()).isNotEqualTo("Jan");
        assertThat(foundDeveloper.getFirstName()).isEqualTo("Igor");
        assertThat(foundDeveloper.getLastName()).isEqualTo("Siotor"); // only lastname should be changed
    }

    @Test
    void DeveloperService_DeleteById_ReturnDeveloperDto() {

        when(developerRepository.findById(anyLong())).thenReturn(Optional.of(developer)); // returns developer by any id

        assertAll(() -> developerService.deleteDeveloper(1L)); // asserts that the delete method was used
        // verifies if deleteDeveloper() can be called without throwing an exception
        verify(developerRepository).deleteById(DEVELOPER_ID); // verifies that this method was called
        verifyNoMoreInteractions(developerRepository);
    }

    @Test
    void DeveloperService_ThrowExceptionOnDelete_ReturnMessage() {
        // given
        when(developerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> developerService.deleteDeveloper(DEVELOPER_ID));
        // then
        assertThat(notFoundException.getMessage()).isEqualTo("Developer not found");
    }

    @Test
    void DeveloperService_Throw_NotFoundException() {
        // exception that should be thrown when not found
        NotFoundException e = assertThrows(NotFoundException.class, () -> developerService.getDeveloperDto(2L));
        // assertion
        assertThat(e.getMessage()).isEqualTo("Developer not found");
    }

    @Test
    void DeveloperService_ThrowNotEqual_NotFoundException() {
        // exception that should be thrown when not found
        NotFoundException e = assertThrows(NotFoundException.class, () -> developerService.getDeveloperDto(2L));
        // assertion
        assertThat(e.getMessage()).isNotEqualTo("");
    }

    @Test
    void DeveloperService_Throw_DeveloperNotFoundWhenDeleting() {
        // given
        when(developerRepository.findById(anyLong())).thenReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> developerService.deleteDeveloper(2L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Developer");
    }
}