package repositories

import enums.ReservationStatus
import interfaces.Searchable
import models.Reservation

class ReservationRepository : Searchable<Reservation> {

    private val reservations = mutableListOf<Reservation>()

    fun addReservation(reservation: Reservation) {
        reservations.add(reservation)
    }

    fun removeReservation(id: Int) {
        reservations.removeIf { it.id == id }
    }

    override fun findById(id: Int): Reservation? {
        return reservations.find { it.id == id }
    }

    override fun findAll(): List<Reservation> {
        return reservations
    }

    fun findByGuestId(guestId: Int): List<Reservation> {
        return reservations.filter { it.guest.id == guestId }
    }

    fun findByRoomId(roomId: Int): List<Reservation> {
        return reservations.filter { it.room.id == roomId }
    }

    fun findByStatus(status: ReservationStatus): List<Reservation> {
        return reservations.filter { it.status == status }
    }

    fun findActiveReservationByRoomId(roomId: Int): Reservation? {
        return reservations.find {
            it.room.id == roomId &&
                    (it.status == ReservationStatus.CREATED || it.status == ReservationStatus.CONFIRMED)
        }
    }
}