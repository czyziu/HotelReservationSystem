package users

import enums.UserRole

class Admin(
    override val id: Int,
    override val firstName: String,
    override val lastName: String,
    override val email: String
) : User(id, firstName, lastName, email, UserRole.ADMIN) {

    override fun displayUserInfo() {
        println("Admin: $firstName $lastName, email: $email")
    }

    override fun login() {
        println("Admin $firstName logged in")
    }

    override fun logout() {
        println("Admin $firstName logged out")
    }

    fun addRoom() {
        println("Adding room...")
    }

    fun removeRoom() {
        println("Removing room...")
    }

    fun updateRoomStatus() {
        println("Updating room status...")
    }

    fun generateReport() {
        println("Generating report...")
    }
}