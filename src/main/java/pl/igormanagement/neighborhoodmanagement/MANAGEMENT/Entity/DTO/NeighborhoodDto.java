package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Developer;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodDto {
    private Long id;
    private Long developerId;
//    @NotEmpty(message = "Name can't be empty")
    private String name;
//    @NotEmpty(message = "City can't be empty")
    private String city;
//    @NotEmpty(message = "Address can't be empty")
    private String address;
}
