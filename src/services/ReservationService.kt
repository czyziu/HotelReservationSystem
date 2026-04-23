package services

import enums.ReservationStatus
import enums.RoomStatus
import exceptions.ReservationNotFoundException
import exceptions.RoomNotAvailableException
import exceptions.RoomNotFoundException
import models.Reservation
import repositories.ReservationRepository
import repositories.RoomRepository
import users.Guest

class ReservationService(
    private val roomRepository: RoomRepository,
    private val reservationRepository: ReservationRepository
) {

    fun createReservation(
        reservationId: Int,
        guest: Guest,
        roomId: Int,
        checkInDate: String,
        checkOutDate: String
    ): Reservation {
        val room = roomRepository.findById(roomId)
            ?: throw RoomNotFoundException("Room with id $roomId not found.")

        if (!room.isAvailable()) {
            throw RoomNotAvailableException("Room with id $roomId is not available.")
        }

        val reservation = Reservation(
            id = reservationId,
            guest = guest,
            room = room,
            checkInDate = checkInDate,
            checkOutDate = checkOutDate,
            status = ReservationStatus.CREATED
        )

        reservationRepository.addReservation(reservation)
        room.updateStatus(RoomStatus.RESERVED)

        return reservation
    }

    fun confirmReservation(reservationId: Int) {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw ReservationNotFoundException("Reservation with id $reservationId not found.")

        reservation.confirmReservation()
    }

    fun cancelReservation(reservationId: Int) {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw ReservationNotFoundException("Reservation with id $reservationId not found.")

        reservation.cancelReservation()
        reservation.room.updateStatus(RoomStatus.AVAILABLE)
    }

    fun checkInReservation(reservationId: Int) {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw ReservationNotFoundException("Reservation with id $reservationId not found.")

        reservation.room.updateStatus(RoomStatus.OCCUPIED)
        reservation.confirmReservation()
    }

    fun checkOutReservation(reservationId: Int) {
        val reservation = reservationRepository.findById(reservationId)
            ?: throw ReservationNotFoundException("Reservation with id $reservationId not found.")

        reservation.completeReservation()
        reservation.room.updateStatus(RoomStatus.AVAILABLE)
    }

    fun getGuestReservations(guestId: Int): List<Reservation> {
        return reservationRepository.findByGuestId(guestId)
    }

    fun getAllReservations(): List<Reservation> {
        return reservationRepository.findAll()
    }

    fun checkRoomAvailability(roomId: Int): Boolean {
        val room = roomRepository.findById(roomId)
            ?: throw RoomNotFoundException("Room with id $roomId not found.")

        return room.isAvailable()
    }

    fun getActiveReservationForRoom(roomId: Int): Reservation? {
        return reservationRepository.findActiveReservationByRoomId(roomId)
    }
}