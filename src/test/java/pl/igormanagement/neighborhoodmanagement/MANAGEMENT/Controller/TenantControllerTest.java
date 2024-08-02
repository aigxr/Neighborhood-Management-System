package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.TenantDtoResponse;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.TenantService;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TenantController.class)
@AutoConfigureMockMvc
class TenantControllerTest {
    private static final Long TENANT_ID = 1L;

    @MockBean
    private TenantService tenantService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Tenant tenant;
    private TenantDto tenantDto;
    private TenantDtoResponse tenantDtoResponse;

    @BeforeEach
    void init() {
        tenant = new Tenant();
        tenant.setId(TENANT_ID);
        tenant.setFirstName("Igor");
        tenant.setLastName("Nowak");

        tenantDto = new TenantDto();
        tenantDto.setId(TENANT_ID);
        tenantDto.setFirstName("Igor");
        tenantDto.setLastName("Nowak");
        tenantDto.setPesel(12341235341L);
        tenantDto.setAddress("Some street");
        tenantDto.setBirthDate(LocalDate.of(2000, 2, 2));

    }

    @Test
    void TenantController_CreateTenant_ReturnTenantDto() throws Exception{
        given(tenantService.createTenant(any(TenantDto.class))).willReturn(tenantDto);

        ResultActions response = mockMvc.perform(post("/create/tenant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tenantDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void TenantController_UpdateTenantById_ReturnTenantDto() throws Exception{
        given(tenantService.updateTenant(anyLong(), any(TenantDto.class))).willReturn(tenantDto);

        ResultActions response = mockMvc.perform(put("/update/tenant/" + TENANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(tenantDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void TenantController_DeleteTenantById_ReturnNothing() throws Exception{
        doNothing().when(tenantService).deleteTenant(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/tenant/" + TENANT_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void TenantController_GetTenantDtoById_ReturnTenantDtoResponse() throws Exception{
        tenantDtoResponse = new TenantDtoResponse();
        tenantDtoResponse.setId(TENANT_ID);
        tenantDtoResponse.setFirstName("Igor");
        tenantDtoResponse.setLastName("Nowak");
        tenantDtoResponse.setPesel(12341235341L);
        tenantDtoResponse.setAddress("Some street");
        tenantDtoResponse.setBirthDate(LocalDate.of(2000, 2, 2));

        given(tenantService.getTenantDto(anyLong())).willReturn(tenantDtoResponse);

        ResultActions response = mockMvc.perform(get("/tenant/" + TENANT_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void TenantController_GetListTenantDtoById_ReturnTenantDto() throws Exception{
        given(tenantService.getAllTenants()).willReturn(Collections.singletonList(tenantDto));

        ResultActions response = mockMvc.perform(get("/tenants")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$[0].firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Nowak")))
                .andExpect(jsonPath("$.size()", CoreMatchers.is(1)))
                .andDo(MockMvcResultHandlers.print());
    }

}