package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.Response;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.ParkingDtoMapper;
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
    public ResponseEntity<List<ParkingDtoResponse>> getAllParking() {
        return ResponseEntity.ok(parkingService.getAllParking());
    }

    @GetMapping("/available/parking")
    public ResponseEntity<List<ParkingDtoResponse>> getAllAvailableParking() {
        return ResponseEntity.ok(parkingService.getAllAvailableParking()
                .stream().map(ParkingDtoMapper::response).toList());
    }

    @GetMapping("/parking/{id}")
    public ResponseEntity<ParkingDtoResponse> getParkingDtoById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(parkingService.getParkingDtoResponse(id));
    }

    @PostMapping("/create/parking")
    public ResponseEntity<?> createParking(@Valid @RequestBody ParkingDto dto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        ParkingDtoResponse savedDto = parkingService.createParking(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }

    @PutMapping("/update/parking/{id}")
    public ResponseEntity<?> updateParking(@PathVariable("id") Long id,
                                         @Valid @RequestBody ParkingDto dto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        ParkingDtoResponse updatedParking = parkingService.updateParking(id, dto);
        return ResponseEntity.ok(updatedParking);
    }

    @DeleteMapping("/delete/parking/{id}")
    public void deleteOwner(@PathVariable("id") Long id) {
        parkingService.deleteParking(id);
    }

    @PostMapping("/parking/{parkingId}/add/{vehicleId}")
    public ResponseEntity<?> addVehicleToParkingSpot(@PathVariable("parkingId") Long parkingId,
                                        @PathVariable("vehicleId") Long vehicleId) {
        parkingService.addVehicle(parkingId, vehicleId);
        return ResponseEntity.ok("Vehicle successfully added to parking spot");
    }
}
