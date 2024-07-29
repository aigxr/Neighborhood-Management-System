package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
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
    public ResponseEntity<List<FlatDto>> getAllFlats() {
        return ResponseEntity.ok(flatService.getAllFlats());
    }

    @GetMapping("/flat/{id}")
    public ResponseEntity<FlatDto> getAllFlats(@PathVariable("id") Long id) {
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
}
