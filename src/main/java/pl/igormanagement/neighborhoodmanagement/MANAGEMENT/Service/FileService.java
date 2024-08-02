package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.ProblematicPersonException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.FileDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.File;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FileRepository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.PersonRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final TenantService tenantService;

    public List<FileDto> getAllFiles() {
        return fileRepository.findAll().stream().map(FileDtoMapper::map).toList();
    }

    public List<FileDtoResponse> getAllFilesByTenantId(Long id) {
        return fileRepository.findAllByTenantId(id).stream().map(FileDtoMapper::response).toList();
    }

    public FileDto getFileDto(Long id) {
        return fileRepository.findById(id).map(FileDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

//    public FileDtoResponse getFileDtoResponse(Long id) {
//        return fileRepository.findById(id).map(FileDtoMapper::response)
//                .orElseThrow(() -> new NotFoundException("File not found"));
//    }

    public File getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

    @Transactional
    public FileDto createFile(FileDto dto) {
        Tenant tenant = tenantService.getTenant(dto.getTenantId());
        File file = new File();
        file.setTitle(dto.getTitle());
        file.setDocument(dto.getDocument());
        file.setTenant(tenant);
        File savedFile = fileRepository.save(file);
        return FileDtoMapper.map(savedFile);
    }

    @Transactional
    public FileDto updateFile(Long id, FileDto dto) {
        Tenant tenant = tenantService.getTenant(dto.getTenantId());
        File file = getFile(id);
        if (dto.getTitle() != null)
            file.setTitle(dto.getTitle());
        if (dto.getDocument() != null)
            file.setDocument(dto.getDocument());
        if (dto.getTenantId() != null)
            file.setTenant(tenant);
        File savedFile = fileRepository.save(file);
        return FileDtoMapper.map(savedFile);
    }

    @Transactional
    public void deleteFile(Long id) {
        File foundFile = getFile(id);
        fileRepository.deleteById(foundFile.getId());
    }
}
