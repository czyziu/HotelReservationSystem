package models

import enums.RoomStatus
import enums.RoomType
import interfaces.Identifiable

data class Room(
    override val id: Int,
    val number: String,
    val type: RoomType,
    val capacity: Int,
    val pricePerNight: Double,
    var status: RoomStatus
) : Identifiable {

    fun updateStatus(newStatus: RoomStatus) {
        status = newStatus
    }

    fun isAvailable(): Boolean {
        return status == RoomStatus.AVAILABLE
    }

    fun displayRoomDetails() {
        println("Room number: $number")
        println("Type: $type")
        println("Capacity: $capacity")
        println("Price per night: $pricePerNight")
        println("Status: $status")
    }
}