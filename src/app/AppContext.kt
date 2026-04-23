package app

import repositories.ReservationRepository
import repositories.RoomRepository
import repositories.UserRepository
import services.AuthService
import services.ReservationService
import services.RoomService

class AppContext {
    val roomRepository = RoomRepository()
    val reservationRepository = ReservationRepository()
    val userRepository = UserRepository()
    val idGenerator = IdGenerator()

    val roomService = RoomService(roomRepository)
    val reservationService = ReservationService(roomRepository, reservationRepository)
    val authService = AuthService(userRepository)

    init {
        val dataInitializer = DataInitializer(
            roomRepository,
            reservationRepository,
            userRepository
        )
        dataInitializer.loadSampleData()
    }
}