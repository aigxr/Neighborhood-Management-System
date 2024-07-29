package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Flat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    // for room area in meters

    @ManyToOne // many flats to one block
    @JoinColumn(name = "block_id")
    private Block block;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "flat", cascade = CascadeType.ALL)
    private List<Person> residents = new ArrayList<>();
}
