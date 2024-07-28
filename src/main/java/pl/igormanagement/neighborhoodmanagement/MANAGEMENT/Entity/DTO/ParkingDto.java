package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

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
    private String name;
    private Double aLength;
    private Double bLength;
}
