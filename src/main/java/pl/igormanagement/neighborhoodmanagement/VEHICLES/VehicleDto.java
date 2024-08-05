package pl.igormanagement.neighborhoodmanagement.VEHICLES;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {
    private Long id;
    @Size(min = 2, max = 50, message = "Name length must be between 2 and 50")
    private String name;
    @Size(min = 5, max = 50, message = "Engine length must be between 2 and 50")
    private String engine;
    @Max(value = 3, message = "A length must be less or equal to 3")
    private Double aLength;
    @Max(value = 2, message = "B length must be less or equal to 2")
    private Double bLength;
    private Double area; // in quadratic meters
}
