package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
