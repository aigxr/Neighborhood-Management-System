package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.File;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FileRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.FILE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    private static final Long FILE_ID = 1L;
    private static final Long TENANT_ID = 1L;

    @Mock private TenantService tenantService;
    @Mock private FileRepository fileRepository;

    private Tenant tenant;
    private File file;
    private FileDto fileDto;
    private FileService fileService;



    @BeforeEach
    void init() {
        fileService = new FileService(fileRepository, tenantService);
        tenant = new Tenant();
        tenant.setId(TENANT_ID);
        tenant.setFirstName("John");

        // expected data
        file = new File();
        file.setId(FILE_ID);
        file.setTitle("Some Title");
        file.setDocument("document.pdf");
        file.setTenant(tenant);

        fileDto = new FileDto();
        fileDto.setId(FILE_ID);
        fileDto.setTitle("New Title");
        fileDto.setDocument("others.pdf");
        fileDto.setTenantId(TENANT_ID);
    }

    @Test
    void FileService_CreateFile_ReturnFileDto() {

        when(tenantService.getTenant(anyLong())).thenReturn(tenant);
        when(fileRepository.save(any(File.class))).thenReturn(file);

        // then

        FileDto createdFile = fileService.createFile(fileDto);
        verify(fileRepository).save(any(File.class));

        assertThat(createdFile.getId()).isEqualTo(FILE_ID);
        assertThat(createdFile.getTitle()).isEqualTo("Some Title");
        assertThat(createdFile.getTenantId()).isEqualTo(tenant.getId());

    }

    @Test
    void FileService_UpdateFileById_ReturnFileDto() {

        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(file));
        when(tenantService.getTenant(anyLong())).thenReturn(tenant);
        when(fileRepository.save(any(File.class))).thenReturn(file);

        // then

        FileDto createdFile = fileService.updateFile(FILE_ID, fileDto);
        verify(fileRepository).save(any(File.class));
        verify(tenantService).getTenant(anyLong());

        assertThat(createdFile.getId()).isEqualTo(FILE_ID);
        assertThat(createdFile.getTitle()).isEqualTo("New Title");
        assertThat(createdFile.getDocument()).isEqualTo("others.pdf");
        assertThat(createdFile.getTenantId()).isEqualTo(tenant.getId());

    }

    @Test
    void FileService_GetFileById_ReturnFileDto() {

        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(file));

        FileDto foundDto = fileService.getFileDto(FILE_ID);
        verify(fileRepository).findById(anyLong());

        assertThat(foundDto.getTitle()).isEqualTo("Some Title");

    }

    @Test
    void FileService_GetAllFiles_ReturnListFileDto() {

        when(fileRepository.findAll()).thenReturn(Collections.singletonList(file));

        List<FileDto> listDto = fileService.getAllFiles();
        verify(fileRepository).findAll();

        FileDto firstDto = listDto.get(0);

        assertThat(firstDto.getTitle()).isEqualTo("Some Title");

    }

    @Test
    void FileService_GetAllFilesByTenantId_ReturnListFileDto() {

        when(fileRepository.findAllByTenantId(anyLong())).thenReturn(Collections.singletonList(file));

        // dtoResponse is just simplified object of FileDto
        List<FileDtoResponse> listDto = fileService.getAllFilesByTenantId(TENANT_ID);
        verify(fileRepository).findAllByTenantId(anyLong());

        FileDtoResponse firstDto = listDto.get(0);

        assertThat(firstDto.getTitle()).isEqualTo("Some Title");
        assertThat(firstDto.getDocument()).isEqualTo("document.pdf");

    }

    @Test
    void FileService_DeleteFileById_ReturnNothing() {

        when(fileRepository.findById(anyLong())).thenReturn(Optional.of(file));

        fileService.deleteFile(FILE_ID);
        verify(fileRepository).deleteById(FILE_ID);
        verifyNoMoreInteractions(fileRepository);

    }

    @Test
    void FileService_ThrowWhenDelete_FileNotFoundException() {

        when(fileRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fileService.deleteFile(FILE_ID))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("File not");

    }

    @Test
    void FileService_ThrowWhenCreate_TenantNotFoundException() {

        when(tenantService.getTenant(anyLong())).thenThrow(new NotFoundException("Tenant not found"));

        assertThatThrownBy(() -> fileService.createFile(fileDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Tenant not");

    }

    @Test
    void FileService_ThrowWhenUpdateById_TenantNotFoundException() {

        when(tenantService.getTenant(anyLong())).thenThrow(new NotFoundException("Tenant not found"));

        assertThatThrownBy(() -> fileService.updateFile(FILE_ID, fileDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("Tenant not");

    }

    @Test
    void FileService_ThrowWhenUpdateById_FileNotFoundException() {

        when(fileRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> fileService.updateFile(FILE_ID, fileDto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageStartingWith("File not");

    }
}