package pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.Mapper;

import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.DTO.RoomDto;
import pl.igormanagement.neighborhoodmanagement.MANAGEMENT.Entity.Room;

public class RoomDtoMapper {
    public static RoomDto map(Room room) {
        RoomDto dto = new RoomDto();
        dto.setId(room.getId());
        dto.setALength(room.getALength());
        dto.setBLength(room.getBLength());
        if (room.getALength() != null && room.getBLength() != null) {
            dto.setRoomArea(room.getALength() * room.getBLength());
        }
        return dto;
    }

    public static Room map(Double aLength, Double bLength) {
        Room room = new Room();
        room.setALength(aLength);
        room.setBLength(bLength);
        if (aLength != null && bLength != null) {
            room.setRoomArea(aLength * bLength);
        }
        return room;
    }

    public static Room mapFoundRoom(Room room, Double aLength, Double bLength) {
        room.setALength(aLength);
        room.setBLength(bLength);
        if (aLength != null && bLength != null) {
            room.setRoomArea(aLength * bLength);
        }
        return room;
    }
}
