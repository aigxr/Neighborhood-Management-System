package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.File;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByTenantId(Long id);
    void deleteAllByTenantId(Long id);
}
