package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
}
