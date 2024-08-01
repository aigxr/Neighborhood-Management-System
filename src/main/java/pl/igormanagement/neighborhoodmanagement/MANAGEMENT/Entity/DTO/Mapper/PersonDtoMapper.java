package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;

public class PersonDtoMapper {
    public static PersonDto map(Person person) {
        PersonDto dto = new PersonDto();
        dto.setId(person.getId());
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        dto.setPesel(person.getPesel());
        dto.setAddress(person.getAddress());
        dto.setBirthDate(person.getBirthDate());
        return dto;
    }

    public static Person map(PersonDto dto) {
        Person person = new Person();
        person.setId(dto.getId());
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setPesel(dto.getPesel());
        person.setAddress(dto.getAddress());
        person.setBirthDate(dto.getBirthDate());
        return person;
    }

    public static Person updateMap(Person person, PersonDto dto) {
        if (dto.getFirstName() != null)
            person.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null)
            person.setLastName(dto.getLastName());
        if (dto.getPesel() != null)
            person.setPesel(dto.getPesel());
        if (dto.getAddress() != null)
            person.setAddress(dto.getAddress());
        if (dto.getBirthDate() != null)
            person.setBirthDate(dto.getBirthDate());
        return person;
    }
}
