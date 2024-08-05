package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.Vehicle;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Boolean isRented;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    public String getIdentifier() {
        return this.getName() + this.getId();
    }
}
