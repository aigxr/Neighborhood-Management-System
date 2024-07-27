package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Tenant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TenantDto {
    private Long id;
    private String firstName;
    private String lastName;
}
