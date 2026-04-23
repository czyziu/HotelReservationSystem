package repositories

import enums.RoomType
import interfaces.Searchable
import models.Room

class RoomRepository : Searchable<Room> {

    private val rooms = mutableListOf<Room>()

    fun addRoom(room: Room) {
        rooms.add(room)
    }

    fun removeRoom(id: Int) {
        rooms.removeIf { it.id == id }
    }

    override fun findById(id: Int): Room? {
        return rooms.find { it.id == id }
    }

    override fun findAll(): List<Room> {
        return rooms
    }

    fun findAvailableRooms(): List<Room> {
        return rooms.filter { it.isAvailable() }
    }

    fun findRoomsByType(type: RoomType): List<Room> {
        return rooms.filter { it.type == type }
    }
}