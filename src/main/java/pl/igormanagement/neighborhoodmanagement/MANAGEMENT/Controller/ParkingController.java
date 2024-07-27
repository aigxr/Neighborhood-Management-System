package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.ParkingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParkingController {
    private final ParkingService parkingService;

    @GetMapping("/parking")
    public ResponseEntity<List<ParkingDto>> getAllParking() {
        return ResponseEntity.ok(parkingService.getAllParking());
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<ParkingDto> getParkingDtoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(parkingService.getParkingDto(id));
    }
}
