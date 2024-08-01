package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NeighborhoodDtoResponse {
    private Long id;
    private Long developerId;
    private String name;
    private String city;
    private String address;
    private List<Block> blockList;
}
