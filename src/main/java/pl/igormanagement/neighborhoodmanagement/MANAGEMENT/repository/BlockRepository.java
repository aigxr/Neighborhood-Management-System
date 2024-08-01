package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Block;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Neighborhood;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByNameAndNeighborhoodId(String name, Long neighborhoodId);
    List<Block> findAllByNeighborhoodId(Long id);
}
