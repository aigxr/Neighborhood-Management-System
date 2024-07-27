package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlockDto {
    private Long id;
    @NotEmpty(message = "Name can't be empty")
    @Size(min = 1, max = 1, message = "Name can be only one letter")
    private String name;
    @NotNull(message = "Neighborhood ID can't be null")
    private Long neighborhoodId;
}
