package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.Vehicle;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingDtoResponse {
    private Long id;
    private String name;
    private Boolean isRented;
    private Room room;
    private Vehicle vehicle;
}
