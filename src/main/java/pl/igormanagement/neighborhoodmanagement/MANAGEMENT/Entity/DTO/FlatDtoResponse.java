package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlatDtoResponse {
    private Long id;
    private String name;
    private Long blockId;
    private Long ownerId;
    private Long tenantId;
    private Room room;
    private Parking parking;
}
