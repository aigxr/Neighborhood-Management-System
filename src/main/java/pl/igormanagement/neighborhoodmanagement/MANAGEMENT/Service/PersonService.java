package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.PersonDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.PersonRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonService {
    private final PersonRepository personRepository;

    public List<PersonDto> getAllPersons() {
        return personRepository.findAll().stream().map(PersonDtoMapper::map).toList();
    }

    public PersonDto getPersonDto(Long id) {
        return personRepository.findById(id).map(PersonDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Person not found"));
    }

    public Person getPerson(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Person not found"));
    }

    public PersonDto createPerson() {
        Person person = new Person();
        return PersonDtoMapper.map(person);
    }



}
