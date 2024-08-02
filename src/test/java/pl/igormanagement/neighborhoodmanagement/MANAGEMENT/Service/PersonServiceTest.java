package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.PersonRepository;

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

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    private static final Long PERSON_ID = 1L;
    @Mock
    private PersonRepository personRepository;

    private PersonService personService;
    private Person person;
    private PersonDto personDto;

    @BeforeEach
    void init() {
        personService = new PersonService(personRepository);

        person = new Person();
        person.setId(PERSON_ID);
        person.setFirstName("Igor");
        person.setLastName("Nowak");

        // THIS IS NOT THE DATA THAT IS GOING TO BE SAVED
        personDto = new PersonDto();
        personDto.setFirstName("Igor");
        personDto.setLastName("Nowak");
        personDto.setPesel(12341235341L);
        personDto.setAddress("Some street");
        personDto.setBirthDate(LocalDate.of(2000, 2, 2));
    }

    @Test
    void PersonService_CreatePerson_ReturnPersonDto() {
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto savedPerson = personService.createPerson(personDto);
        verify(personRepository).save(any(Person.class));

        assertThat(savedPerson.getFirstName()).isEqualTo("Igor");
        assertThat(savedPerson.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void PersonService_UpdatePersonById_ReturnPersonDto() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        PersonDto savedPerson = personService.updatePerson(PERSON_ID, personDto);
        verify(personRepository).save(any(Person.class));

        assertThat(savedPerson.getFirstName()).isEqualTo("Igor");
        assertThat(savedPerson.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void PersonService_DeletePersonById_ReturnNothing() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        personService.deletePerson(PERSON_ID);
        verify(personRepository).deleteById(anyLong());
        verifyNoMoreInteractions(personRepository);
    }

    @Test
    void PersonService_GetPersonById_ReturnPersonDto() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        PersonDto foundPerson = personService.getPersonDto(PERSON_ID);
        verify(personRepository).findById(anyLong());

        assertThat(foundPerson.getFirstName()).isEqualTo("Igor");
        assertThat(foundPerson.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void PersonService_GetAllPersons_ReturnListPersonDto() {
        when(personRepository.findAll()).thenReturn(Collections.singletonList(person));

        List<PersonDto> listOfPeople = personService.getAllPersons();
        verify(personRepository).findAll();

        PersonDto firstPerson = listOfPeople.get(0);
        assertThat(firstPerson.getFirstName()).isEqualTo("Igor");
        assertThat(firstPerson.getLastName()).isEqualTo("Nowak");
    }

    @Test
    void PersonService_ThrowWhenUpdate_NotFoundException() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> personService.updatePerson(PERSON_ID, personDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Person not");
    }

    @Test
    void PersonService_ThrowWhenDelete_NotFoundException() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> personService.deletePerson(PERSON_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Person not");
    }
}