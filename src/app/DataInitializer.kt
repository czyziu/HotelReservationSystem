package app

import enums.ReservationStatus
import enums.RoomStatus
import enums.RoomType
import models.Room
import repositories.ReservationRepository
import repositories.RoomRepository
import repositories.UserRepository
import services.AuthService
import services.ReservationService
import services.RoomService
import users.Admin
import users.Guest
import users.Receptionist

class DataInitializer(
    private val roomRepository: RoomRepository,
    private val reservationRepository: ReservationRepository,
    private val userRepository: UserRepository
) {

    val roomService = RoomService(roomRepository)
    val reservationService = ReservationService(roomRepository, reservationRepository)
    val authService = AuthService(userRepository)

    fun loadSampleData() {
        loadRooms()
        loadUsers()
        loadSampleReservations()
    }

    private fun loadRooms() {
        roomService.addRoom(
            Room(101, "A101", RoomType.SINGLE, 1, 200.0, RoomStatus.AVAILABLE)
        )
        roomService.addRoom(
            Room(102, "A102", RoomType.DOUBLE, 2, 300.0, RoomStatus.AVAILABLE)
        )
        roomService.addRoom(
            Room(103, "A103", RoomType.APARTMENT, 4, 600.0, RoomStatus.OCCUPIED)
        )
        roomService.addRoom(
            Room(104, "A104", RoomType.DOUBLE, 2, 350.0, RoomStatus.AVAILABLE)
        )
    }

    private fun loadUsers() {
        userRepository.addUser(
            Admin(1, "System", "Admin", "admin@admin")
        )
        userRepository.addUser(
            Receptionist(2, "Main", "Reception", "reception@reception")
        )
    }

    private fun loadSampleReservations() {
        val occupiedRoom = roomRepository.findById(103)

        if (occupiedRoom != null) {
            reservationRepository.addReservation(
                models.Reservation(
                    id = 100,
                    guest = Guest(
                        id = 100,
                        firstName = "Adam",
                        lastName = "Nowak",
                        email = "adam.nowak@test.pl"
                    ),
                    room = occupiedRoom,
                    checkInDate = "2026-04-20",
                    checkOutDate = "2026-04-27",
                    status = ReservationStatus.CONFIRMED
                )
            )
        }
    }
}