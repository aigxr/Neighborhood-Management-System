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
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne // many blocks to one neighborhood
    @JoinColumn(name = "neighborhood_id")
    private Neighborhood neighborhood;

    @OneToMany(mappedBy = "block", cascade = CascadeType.MERGE)
    private List<Flat> flatList = new ArrayList<>(6);

    public String getBlockIdentifier() {
        return this.name + this.id;
    }
}
