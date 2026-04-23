package app

class IdGenerator {
    private var nextRoomId = 105
    private var nextReservationId = 1
    private var nextGuestId = 1

    fun getNextRoomId(): Int {
        return nextRoomId++
    }

    fun getNextReservationId(): Int {
        return nextReservationId++
    }

    fun getNextGuestId(): Int {
        return nextGuestId++
    }
}