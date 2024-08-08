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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.PersonDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service.PersonService;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PersonController.class)
@AutoConfigureMockMvc
class PersonControllerTest {
    private static final Long PERSON_ID = 1L;

    @MockBean
    private PersonService personService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Person person;
    private PersonDto personDto;

    @BeforeEach
    void init() {
        person = new Person();
        person.setId(PERSON_ID);
        person.setFirstName("Igor");
        person.setLastName("Nowak");

        personDto = new PersonDto();
        personDto.setId(PERSON_ID);
        personDto.setFirstName("Igor");
        personDto.setLastName("Nowak");
        personDto.setPesel(12341235341L);
        personDto.setAddress("Some street");
        personDto.setBirthDate(LocalDate.of(2000, 2, 2));
    }

    @Test
    void PersonController_CreatePerson_ReturnPersonDto() throws Exception{
        given(personService.createPerson(any(PersonDto.class))).willReturn(personDto);

        ResultActions response = mockMvc.perform(post("/create/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)));

        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")));
    }

    @Test
    void PersonController_UpdatePersonById_ReturnPersonDto() throws Exception{
        given(personService.updatePerson(anyLong(), any(PersonDto.class))).willReturn(personDto);

        ResultActions response = mockMvc.perform(put("/update/person/" + PERSON_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(personDto)));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")));
    }

    @Test
    void PersonController_DeletePersonById_ReturnPersonDto() throws Exception{
        doNothing().when(personService).deletePerson(anyLong());

        ResultActions response = mockMvc.perform(delete("/delete/person/" + PERSON_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    void PersonController_GetPersonDtoById_ReturnPersonDto() throws Exception{
        given(personService.getPersonDto(anyLong())).willReturn(personDto);

        ResultActions response = mockMvc.perform(get("/person/" + PERSON_ID)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$.firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is("Nowak")));
    }

    @Test
    void PersonController_GetAllPersonDtoById_ReturnListPersonDto() throws Exception{
        given(personService.getAllPersons()).willReturn(Collections.singletonList(personDto));

        ResultActions response = mockMvc.perform(get("/person")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", CoreMatchers.notNullValue()))
                .andExpect(jsonPath("$[0].firstName", CoreMatchers.is("Igor")))
                .andExpect(jsonPath("$[0].lastName", CoreMatchers.is("Nowak")))
                .andExpect(jsonPath("$[0].birthDate", CoreMatchers.is(LocalDate.of(2000, 2, 2).toString())));
    }

}