package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDto {
    private Long id;
    @Size(min = 1, max = 1, message = "Name must be provided. Length (1)")
    private String name;
    @NotNull(message = "A length must be provided")
    private Double aLength;
    @NotNull(message = "B length must be provided")
    private Double bLength;
    private Double freeArea;
    private Boolean isRented;
    private Long vehicleId;
}
