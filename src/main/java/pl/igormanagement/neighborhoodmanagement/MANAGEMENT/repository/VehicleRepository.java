package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}

