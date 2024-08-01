package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private Long pesel;
    private String address;
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "flat_id")
    private Flat flat;
}
