package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Long pesel;
    private String address;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "developer", cascade = CascadeType.ALL)
    private List<Neighborhood> neighborhoodList;

}
