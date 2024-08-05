package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Parking;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Optional<Parking> findByName(String name);
    List<Parking> findAllByIsRentedFalse();
}
