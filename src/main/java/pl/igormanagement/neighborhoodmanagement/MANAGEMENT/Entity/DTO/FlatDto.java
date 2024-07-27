package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlatDto {
    private Long id;
    private Double aLength;
    private Double bLength;
    private Long blockId;
    private Long ownerId;
    private Long tenantId;
    private Long roomId;
    private Long rentalId;
}
