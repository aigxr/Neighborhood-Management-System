package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.ParkingDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;
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

    @PostMapping("/create/parking")
    public ResponseEntity<?> createParking(@Valid @RequestBody ParkingDto dto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        ParkingDtoResponse savedDto = parkingService.createParking(dto);
        return ResponseEntity.ok(savedDto);
    }
//    @PutMapping("/update/owner/{id}")
//    public ResponseEntity<?> updateParking(@PathVariable("id") Long id,
//                                         @Valid @RequestBody ParkingDto dto,
//                                         BindingResult result) {
//        if (result.hasErrors()) {
//            List<String> errors = StaticMethods.checkForErrors(result);
//            return ResponseEntity.badRequest().body(errors);
//        }
//        OwnerDto updatedOwner = ownerService.updateOwner(id, dto);
//        return ResponseEntity.ok(updatedOwner);
//    }

    @DeleteMapping("/delete/parking/{id}")
    public void deleteOwner(@PathVariable("id") Long id) {
        parkingService.deleteParking(id);
    }
}
