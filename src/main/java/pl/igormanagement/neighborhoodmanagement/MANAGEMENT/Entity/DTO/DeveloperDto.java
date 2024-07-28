package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDto {
    private Long id;
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    @NotBlank(message = "First name cannot be blank")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;
    @NotNull(message = "PESEL cannot be null")
    @Digits(integer = 11, fraction = 0, message = "PESEL must be a number of 11 digits")
    @Min(value = 10000000000L, message = "PESEL must be at least 11 digits")
    @Max(value = 99999999999L, message = "PESEL cannot be more than 11 digits")
    private Long PESEL;
    @NotBlank(message = "Address cannot be blank")
    @Size(min = 5, max = 300, message = "Address must be between 5 and 300 characters")
    private String address;
    @Past(message = "Birth date must be in the past")
    @NotNull(message = "Birth date cannot be null")
    private LocalDate birthDate;
}
