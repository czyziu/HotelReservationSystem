package models

import enums.ReservationStatus
import interfaces.Identifiable
import users.Guest

data class Reservation(
    override val id: Int,
    val guest: Guest,
    val room: Room,
    val checkInDate: String,
    val checkOutDate: String,
    var status: ReservationStatus
) : Identifiable {

    fun confirmReservation() {
        status = ReservationStatus.CONFIRMED
    }

    fun cancelReservation() {
        status = ReservationStatus.CANCELLED
    }

    fun completeReservation() {
        status = ReservationStatus.COMPLETED
    }

    fun getReservationDetails() {
        println("Reservation id: $id")
        println("Guest: ${guest.firstName} ${guest.lastName}")
        println("Room: ${room.number}")
        println("Check-in: $checkInDate")
        println("Check-out: $checkOutDate")
        println("Status: $status")
    }
}