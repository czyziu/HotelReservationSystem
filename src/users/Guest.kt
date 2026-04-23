package users

import enums.UserRole

class Guest(
    override val id: Int,
    override val firstName: String,
    override val lastName: String,
    override val email: String
) : User(id, firstName, lastName, email, UserRole.GUEST) {

    override fun displayUserInfo() {
        println("Guest: $firstName $lastName, email: $email")
    }

    override fun login() {
        println("$firstName logged in")
    }

    override fun logout() {
        println("$firstName logged out")
    }

    fun makeReservation() {
        println("Making reservation...")
    }
}