package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import jakarta.validation.executable.ExecutableType;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.FlatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlatController {
    private final FlatService flatService;

    @GetMapping("/flats")
    public ResponseEntity<List<FlatDtoResponse>> getAllFlats() {
        return ResponseEntity.ok(flatService.getAllFlats());
    }

    @GetMapping("/flat/{id}")
    public ResponseEntity<FlatDtoResponse> getAllFlats(@PathVariable("id") Long id) {
        return ResponseEntity.ok(flatService.getFlatDto(id));
    }

    @PostMapping("/create/flat")
    public ResponseEntity<?> createFlat(@Valid @RequestBody FlatDto dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        FlatDtoResponse createdFlat = flatService.createFlat(dto);
        return ResponseEntity.ok(createdFlat);
    }

    // PUTS NEW FLAT BUT ALSO CHANGES THE ROOM
    @PutMapping("/update/flat/{id}")
    public ResponseEntity<?> updateFlat(@PathVariable("id") Long id,
                                        @Valid @RequestBody FlatDto dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        FlatDtoResponse updatedFlat = flatService.updateFlat(id, dto);
        return ResponseEntity.ok(updatedFlat);
    }

    @DeleteMapping("/delete/flat/{id}")
    public void deleteFlat(@PathVariable("id") Long id) {
        flatService.deleteFlat(id);
    }
}
