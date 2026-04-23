package users

import enums.UserRole

class Receptionist(
    override val id: Int,
    override val firstName: String,
    override val lastName: String,
    override val email: String
) : User(id, firstName, lastName, email, UserRole.RECEPTIONIST) {

    override fun displayUserInfo() {
        println("Receptionist: $firstName $lastName, email: $email")
    }

    override fun login() {
        println("Receptionist $firstName logged in")
    }

    override fun logout() {
        println("Receptionist $firstName logged out")
    }

    fun createReservationForGuest() {
        println("Creating reservation for guest...")
    }

    fun checkInGuest() {
        println("Checking in guest...")
    }

    fun checkOutGuest() {
        println("Checking out guest...")
    }

    fun assignRoom() {
        println("Assigning room...")
    }
}