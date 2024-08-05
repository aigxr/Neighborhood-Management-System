package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.AllSpaceTakenException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.ItemTooBigException;
import pl.igormanagement.neighborhoodmanagement.EXCEPTIONS.NotFoundException;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.repository.VehicleRepository;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.Vehicle;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.VehicleDto;
import pl.igormanagement.neighborhoodmanagement.VEHICLES.VehicleDtoMapper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Vehicle getVehicle(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vehicle not found"));
    }

    public Vehicle createVehicle(VehicleDto dto) {
        Vehicle mappedVehicle = VehicleDtoMapper.map(dto);
        return vehicleRepository.save(mappedVehicle);
    }

    public Vehicle updateVehicle(Long id, VehicleDto dto) {
        Vehicle foundVehicle = getVehicle(id);
        Vehicle mappedVehicle = VehicleDtoMapper.updateMap(foundVehicle, dto);
        return vehicleRepository.save(mappedVehicle);
    }

    public void deleteVehicle(Long id) {
        Vehicle vehicle = getVehicle(id);
        vehicleRepository.deleteById(vehicle.getId());
    }
}
