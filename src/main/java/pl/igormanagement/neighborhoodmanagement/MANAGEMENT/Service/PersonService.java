package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.PersonDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FlatRepository;
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

    @Transactional
    public PersonDto createPerson(PersonDto dto) {
        Person mappedPerson = PersonDtoMapper.map(dto);
        Person savedPerson = personRepository.save(mappedPerson);
        return PersonDtoMapper.map(savedPerson);
    }

    @Transactional
    public PersonDto updatePerson(Long id, PersonDto dto) {
        Person foundPerson = getPerson(id);
        Person mappedPerson = PersonDtoMapper.updateMap(foundPerson, dto);

        Person savedPerson = personRepository.save(mappedPerson);
        return PersonDtoMapper.map(savedPerson);
    }

    @Transactional
    public void deletePerson(Long id) {
        Person person = getPerson(id);
        personRepository.deleteById(person.getId());
    }
}
