package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper.RentalDtoMapper;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RentalDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Rental;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.RentalRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentalService {
    private final RentalRepository rentalRepository;

    public List<RentalDto> getAllRentals() {
        return rentalRepository.findAll().stream().map(RentalDtoMapper::map).toList();
    }

    public RentalDto getRentalDto(Long id) {
        return rentalRepository.findById(id).map(RentalDtoMapper::map)
                .orElseThrow(() -> new NotFoundException("Rental not found"));
    }

    public Rental getRental(Long id) {
        return rentalRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rental not found"));
    }

    public RentalDto createRental() {
        Rental rental = new Rental();
        return RentalDtoMapper.map(rental);
    }
}
