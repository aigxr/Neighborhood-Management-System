package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeveloperDto {
    private Long id;
    @Size(min = 3, max = 50, message = "Length of first name needs to be equal or more than three, less than 50")
    private String firstName;
    @Size(min = 3, max = 50, message = "Length of last name needs to be equal or more than three, less than 50")
    private String lastName;
}
