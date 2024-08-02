package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.PersonService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PersonController {
    private final PersonService personService;

    @GetMapping("/person")
    public ResponseEntity<List<PersonDto>> getAllPersons() {
        return ResponseEntity.ok(personService.getAllPersons());
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<PersonDto> getPersonDtoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(personService.getPersonDto(id));
    }

    @PostMapping("/create/person")
    public ResponseEntity<?> createPerson(@Valid @RequestBody PersonDto dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        PersonDto savedPerson = personService.createPerson(dto);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @PutMapping("/update/person/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable("id") Long id,
                                          @Valid @RequestBody PersonDto dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        PersonDto updatedPerson = personService.updatePerson(id, dto);
        return ResponseEntity.ok(updatedPerson);
    }

    @DeleteMapping("/delete/person/{id}")
    public void deletePerson(@PathVariable("id") Long id) {
        personService.deletePerson(id);
    }
}
