package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.ADDITIONALS.StaticMethods;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.FileService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    @GetMapping("/file")
    public ResponseEntity<List<FileDto>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    @GetMapping("/file/tenant/{id}")
    public ResponseEntity<List<FileDtoResponse>> getAllFilesByTenantId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fileService.getAllFilesByTenantId(id));
    }


    @GetMapping("/file/{id}")
    public ResponseEntity<FileDto> getFileDto(@PathVariable("id") Long id) {
        return ResponseEntity.ok(fileService.getFileDto(id));
    }

    @PostMapping("/create/file")
    public ResponseEntity<?> createFile(@Valid @RequestBody FileDto dto,
                                        BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = StaticMethods.checkForErrors(result);
            return ResponseEntity.badRequest().body(errors);
        }
        FileDto savedFile = fileService.createFile(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedFile);
    }
}
