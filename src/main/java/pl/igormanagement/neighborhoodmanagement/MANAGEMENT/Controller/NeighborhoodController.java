package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.NeighborhoodDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.NeighborhoodDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.NeighborhoodService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NeighborhoodController {
    private final NeighborhoodService neighborhoodService;

    @GetMapping("/neighborhood")
    public ResponseEntity<List<NeighborhoodDto>> getAllNeighborhoods() {
        return ResponseEntity.ok(neighborhoodService.getAllNeighborhoods());
    }

    @GetMapping("/neighborhood/developer/{id}")
    public ResponseEntity<List<NeighborhoodDto>> getAllNeighborhoodsByDeveloperId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(neighborhoodService.getNeighborhoodsByDeveloperId(id));
    }

    @GetMapping("/neighborhood/{id}")
    public ResponseEntity<NeighborhoodDto> getNeighborhoodById(@PathVariable Long id) {
        return ResponseEntity.ok(neighborhoodService.getNeighborhoodDto(id));
    }

    @PostMapping("/create/neighborhood")
    public ResponseEntity<?> createNeighborhood(@Valid @RequestBody NeighborhoodDto dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        NeighborhoodDto neighborhood = neighborhoodService.createNeighborhood(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(neighborhood);
    }

    @PatchMapping("/update/neighborhood/{id}")
    public ResponseEntity<?> updateNeighborhood(@PathVariable(value = "id") Long id,
                                                @Valid @RequestBody NeighborhoodDto dto,
                                                BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        NeighborhoodDto updatedDto = neighborhoodService.updateNeighborhood(id, dto); // NEEDS TO RETURN TO BE CHECKED IN TESTS
        return ResponseEntity.ok().body(updatedDto);
    }

    @DeleteMapping("/delete/neighborhood/{id}")
    public void deleteNeighborhood(@PathVariable(value = "id") Long id) {
        neighborhoodService.deleteNeighborhood(id);
    }

    private static List<String> checkForErrors(BindingResult result) {
        return result.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

}
