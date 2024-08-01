package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Flat;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OwnerDtoResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private Long pesel;
    private String address;
    private LocalDate birthDate;
    private List<FlatDtoResponse> flats;
}
