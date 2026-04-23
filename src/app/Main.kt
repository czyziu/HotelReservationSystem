package app

import enums.RoomStatus
import enums.RoomType
import models.Room
import repositories.ReservationRepository
import repositories.RoomRepository
import services.ReservationService
import users.Guest

fun main() {
    val roomRepository = RoomRepository()
    val reservationRepository = ReservationRepository()
    val reservationService = ReservationService(roomRepository, reservationRepository)

    val guest = Guest(1, "Jan", "Kowalski", "jan@test.pl")

    val room1 = Room(
        id = 101,
        number = "A101",
        type = RoomType.SINGLE,
        capacity = 1,
        pricePerNight = 200.0,
        status = RoomStatus.AVAILABLE
    )

    val room2 = Room(
        id = 102,
        number = "A102",
        type = RoomType.DOUBLE,
        capacity = 2,
        pricePerNight = 300.0,
        status = RoomStatus.AVAILABLE
    )

    val room3 = Room(
        id = 103,
        number = "A103",
        type = RoomType.APARTMENT,
        capacity = 4,
        pricePerNight = 600.0,
        status = RoomStatus.OCCUPIED
    )

    roomRepository.addRoom(room1)
    roomRepository.addRoom(room2)
    roomRepository.addRoom(room3)

    println("AVAILABLE ROOMS BEFORE RESERVATION:")
    roomRepository.findAvailableRooms().forEach {
        it.displayRoomDetails()
        println()
    }

    val reservation = reservationService.createReservation(
        reservationId = 1,
        guest = guest,
        roomId = 102,
        checkInDate = "2025-06-10",
        checkOutDate = "2025-06-15"
    )

    println("CREATED RESERVATION:")
    reservation.getReservationDetails()
    println()

    println("AVAILABLE ROOMS AFTER RESERVATION:")
    roomRepository.findAvailableRooms().forEach {
        it.displayRoomDetails()
        println()
    }

    reservationService.confirmReservation(1)

    println("RESERVATION AFTER CONFIRM:")
    reservationRepository.findById(1)?.getReservationDetails()
    println()

    reservationService.cancelReservation(1)

    println("RESERVATION AFTER CANCELLATION:")
    reservationRepository.findById(1)?.getReservationDetails()
    println()

    println("AVAILABLE ROOMS AFTER CANCELLATION:")
    roomRepository.findAvailableRooms().forEach {
        it.displayRoomDetails()
        println()
    }
}