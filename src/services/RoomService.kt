package services

import enums.RoomStatus
import exceptions.RoomAlreadyExistsException
import exceptions.RoomNotFoundException
import models.Room
import repositories.RoomRepository

class RoomService(
    private val roomRepository: RoomRepository
) {

    fun addRoom(room: Room) {
        val existingRoom = roomRepository.findById(room.id)
        if (existingRoom != null) {
            throw RoomAlreadyExistsException("Room with id ${room.id} already exists.")
        }

        roomRepository.addRoom(room)
    }

    fun removeRoom(roomId: Int) {
        val room = roomRepository.findById(roomId)
            ?: throw RoomNotFoundException("Room with id $roomId not found.")

        roomRepository.removeRoom(roomId)
    }

    fun updateRoomStatus(roomId: Int, newStatus: RoomStatus) {
        val room = roomRepository.findById(roomId)
            ?: throw RoomNotFoundException("Room with id $roomId not found.")

        room.updateStatus(newStatus)
    }

    fun getAllRooms(): List<Room> {
        return roomRepository.findAll()
    }

    fun getAvailableRooms(): List<Room> {
        return roomRepository.findAvailableRooms()
    }
}