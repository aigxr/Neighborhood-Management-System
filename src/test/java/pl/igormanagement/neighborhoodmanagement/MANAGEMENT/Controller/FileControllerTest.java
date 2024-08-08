package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FileDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.FlatDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.File;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.FileService;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.TenantService;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.FileRepository;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FileController.class)
@AutoConfigureMockMvc
class FileControllerTest {

    private static final Long FILE_ID = 1L;
    private static final Long TENANT_ID = 1L;

    @MockBean
    private FileService fileService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;
    private Tenant tenant;
    private File file;
    private FileDto fileDto;

    @BeforeEach
    void init() {
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
    void FileController_CreateFile_ReturnFileDto() throws Exception {
        given(fileService.createFile(any(FileDto.class))).willReturn(fileDto);

        ResultActions response = mockMvc.perform(post("/create/file")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fileDto)));

        verify(fileService).createFile(any(FileDto.class));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New Title")))
                .andExpect(jsonPath("$.document", is("others.pdf")));

    }


    @Test
    void FileController_UpdateFileById_ReturnFileDto() throws Exception {
        given(fileService.updateFile(anyLong(), any(FileDto.class))).willReturn(fileDto);

        ResultActions response = mockMvc.perform(put("/update/file/" + FILE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fileDto)));

        verify(fileService).updateFile(anyLong(), any(FileDto.class));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New Title")))
                .andExpect(jsonPath("$.document", is("others.pdf")));

    }

    @Test
    void FileController_DeleteFileById_ReturnNothing() throws Exception {
        doNothing().when(fileService).deleteFile(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/file/" + FILE_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(fileService).deleteFile(anyLong());

        response.andExpect(status().isOk());

    }


    @Test
    void FileController_GetFileById_ReturnFileDto() throws Exception {
        given(fileService.getFileDto(anyLong())).willReturn(fileDto);

        ResultActions response = mockMvc.perform(get("/file/" + FILE_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(fileService).getFileDto(anyLong());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("New Title")))
                .andExpect(jsonPath("$.document", is("others.pdf")));

    }

    @Test
    void FileController_GetAllFiles_ReturnListFileDto() throws Exception {
        given(fileService.getAllFiles()).willReturn(Collections.singletonList(fileDto));

        ResultActions response = mockMvc.perform(get("/files")
                .contentType(MediaType.APPLICATION_JSON));

        verify(fileService).getAllFiles();

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("New Title")))
                .andExpect(jsonPath("$[0].document", is("others.pdf")));
    }

    @Test
    void FileController_GetAllFilesByTenantId_ReturnListFileDto() throws Exception {
        // given
        FileDtoResponse fileDtoResponse = new FileDtoResponse();
        fileDtoResponse.setId(FILE_ID);
        fileDtoResponse.setTitle("Response Title");

        given(fileService.getAllFilesByTenantId(anyLong())).willReturn(Collections.singletonList(fileDtoResponse));

        // when
        ResultActions response = mockMvc.perform(get("/file/tenant/" + TENANT_ID)
                .contentType(MediaType.APPLICATION_JSON));

        verify(fileService).getAllFilesByTenantId(anyLong());

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Response Title")));

    }
}