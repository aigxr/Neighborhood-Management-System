package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.BlockDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.BlockService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BlockController {
    private final BlockService blockService;

    @GetMapping("/blocks")
    public ResponseEntity<List<BlockDto>> getAllBlocks() {
        return ResponseEntity.ok(blockService.getAllBlocks());
    }

    @GetMapping("/block/{id}")
    public ResponseEntity<?> getBlock(@PathVariable(value = "id") Long id) {
        BlockDto dto = blockService.getBlockDto(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create/block")
    public ResponseEntity<?> createBlock(@Valid @RequestBody BlockDto dto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        BlockDto blockDto = blockService.createBlock(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(blockDto);
    }

    @PutMapping("/update/block/{id}")
    public ResponseEntity<?> updateBlock(@PathVariable(value = "id") Long id,
                                         @Valid @RequestBody BlockDto dto,
                                         BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        BlockDto updatedBlock = blockService.updateBlock(id, dto);
        return ResponseEntity.ok(updatedBlock);
    }

    @DeleteMapping("/delete/block/{id}")
    public void deleteBlock(@PathVariable("id") Long id) {
        blockService.deleteBlock(id);
    }

    private static List<String> checkForErrors(BindingResult result) {
        return result.getAllErrors()
                .stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
    }
}
