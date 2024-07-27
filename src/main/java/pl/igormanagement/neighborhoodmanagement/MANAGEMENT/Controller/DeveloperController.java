package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.DeveloperDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.DeveloperService;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DeveloperController {
    private final DeveloperService developerService;

    @GetMapping("/developer")
    public ResponseEntity<List<DeveloperDto>> getAllDevelopers() {
        return ResponseEntity.ok(developerService.getAllDevelopers());
    }

    @GetMapping("/developer/{id}")
    public ResponseEntity<DeveloperDto> getDeveloper(@PathVariable Long id) {
        DeveloperDto developer = developerService.getDeveloperDto(id);
        if (developer != null) {
            return ResponseEntity.ok(developer);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add/developer")
    public ResponseEntity<?> createDeveloper(@Valid @RequestBody DeveloperDto dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        DeveloperDto developer = developerService.createDeveloper(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(developer);
    }

    @PatchMapping("/update/developer/{id}")
    public ResponseEntity<?> updateDeveloper(@PathVariable Long id,
                                             @Valid @RequestBody DeveloperDto dto,
                                             BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        DeveloperDto savedDto = developerService.updateDeveloper(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(savedDto);
    }

    @DeleteMapping("/delete/developer/{id}")
    public void deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
    }

    private static List<String> checkForErrors(BindingResult result) {
        return result.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
