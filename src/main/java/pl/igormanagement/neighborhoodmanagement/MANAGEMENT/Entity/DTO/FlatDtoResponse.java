package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
