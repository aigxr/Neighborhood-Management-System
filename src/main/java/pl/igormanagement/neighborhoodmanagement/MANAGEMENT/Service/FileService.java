package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.ProblematicPersonException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDto;
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

    public List<FileDto> getAllFilesByTenantId(Long id) {
        return fileRepository.findAllByTenantId(id).stream().map(FileDtoMapper::map).toList();
    }

    public FileDto getFileDto(Long id) {
        return fileRepository.findById(id).map(FileDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

    public File getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("File not found"));
    }

    @Transactional
    public FileDto createFile(FileDto fileDto) {
        Tenant tenant = tenantService.getTenant(fileDto.getTenantId());
        File file = new File();
        file.setTitle(fileDto.getTitle());
        file.setDocument(fileDto.getDocument());
        file.setTenant(tenant);
        File savedFile = fileRepository.save(file);
        return FileDtoMapper.map(savedFile);
    }
}
