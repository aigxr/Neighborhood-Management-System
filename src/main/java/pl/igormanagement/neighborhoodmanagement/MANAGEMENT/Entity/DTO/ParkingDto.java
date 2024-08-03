package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Name identifier must be provided")
    private String name;
    @NotNull(message = "A length must be provided")
    private Double aLength;
    @NotNull(message = "B length must be provided")
    private Double bLength;
    private Boolean isRented;
}
