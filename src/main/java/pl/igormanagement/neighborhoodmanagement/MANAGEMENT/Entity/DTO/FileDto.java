package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Person;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private Long id;
    @Size(min = 3, max = 50, message = "Length of title needs to be equal or more than three, less than 50")
    private String title;
    @Size(min = 3, max = 50, message = "Length of first name needs to be equal or more than three, less than 50")
    private String document;
    @NotNull(message = "Tenant ID can't be empty")
    private Long tenantId;
}
