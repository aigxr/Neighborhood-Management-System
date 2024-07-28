package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Block ID must be provided")
    private Long blockId;
    @NotNull(message = "Block ID must be provided")
    private Long ownerId;
    @NotNull(message = "Block ID must be provided")
    private Long tenantId;
    private Long roomId;
    private Long rentalId;
}
