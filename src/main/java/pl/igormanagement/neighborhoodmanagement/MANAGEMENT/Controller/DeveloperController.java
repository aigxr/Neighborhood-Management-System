package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.DeveloperDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.DeveloperService;

import java.util.List;

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

    @PostMapping("/create/developer")
    public ResponseEntity<?> createDeveloper(@Valid @RequestBody DeveloperDto dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
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
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        DeveloperDto savedDto = developerService.updateDeveloper(id, dto);
        return ResponseEntity.status(HttpStatus.OK).body(savedDto);
    }

    @DeleteMapping("/delete/developer/{id}")
    public void deleteDeveloper(@PathVariable Long id) {
        developerService.deleteDeveloper(id);
    }
}
