package pl.igormanagement.neighborhoodmanagement.VEHICLES;

public class VehicleDtoMapper {

    public static Vehicle map(VehicleDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setName(dto.getName());
        vehicle.setEngine(dto.getEngine());
        vehicle.setALength(dto.getALength());
        vehicle.setBLength(dto.getBLength());
        return vehicle;
    }

    public static Vehicle updateMap(Vehicle vehicle, VehicleDto dto) {
        if (dto.getName() != null)
            vehicle.setName(dto.getName());
        if (dto.getEngine() != null)
            vehicle.setEngine(dto.getEngine());
        if (dto.getALength() != null)
            vehicle.setALength(dto.getALength());
        if (dto.getBLength() != null)
            vehicle.setBLength(dto.getBLength());
        return vehicle;
    }

    public static VehicleDto map(Vehicle vehicle) {
        VehicleDto dto = new VehicleDto();
        dto.setId(vehicle.getId());
        dto.setName(vehicle.getName());
        dto.setEngine(vehicle.getEngine());
        dto.setALength(vehicle.getALength());
        dto.setBLength(vehicle.getBLength());
        return dto;
    }
}
