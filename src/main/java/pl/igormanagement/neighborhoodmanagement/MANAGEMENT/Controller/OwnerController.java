package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.OwnerDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.OwnerService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OwnerController {
    private final OwnerService ownerService;

    @GetMapping("/owners")
    public ResponseEntity<List<OwnerDto>> getAllOwners() {
        return ResponseEntity.ok(ownerService.getAllOwners());
    }

    @GetMapping("/owner/{id}")
    public ResponseEntity<OwnerDtoResponse> getOwnerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ownerService.getOwnerDtoResponse(id));
    }

    @PostMapping("/create/owner")
    public ResponseEntity<?> createOwner(@Valid @RequestBody OwnerDto dto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        OwnerDto savedDto = ownerService.createOwner(dto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);
    }
    @PutMapping("/update/owner/{id}")
    public ResponseEntity<?> updateOwner(@PathVariable("id") Long id,
                                          @Valid @RequestBody OwnerDto dto,
                                          BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        OwnerDto updatedOwner = ownerService.updateOwner(id, dto);
        return ResponseEntity.ok(updatedOwner);
    }

    @DeleteMapping("/delete/owner/{id}")
    public void deleteOwner(@PathVariable("id") Long id) {
        ownerService.deleteOwner(id);
    }

}
