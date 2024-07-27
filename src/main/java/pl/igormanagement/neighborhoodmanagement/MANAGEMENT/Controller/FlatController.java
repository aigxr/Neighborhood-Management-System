package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
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
}
