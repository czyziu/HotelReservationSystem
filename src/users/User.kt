package users

import enums.UserRole
import interfaces.Identifiable

abstract class User(
    override val id: Int,
    open val firstName: String,
    open val lastName: String,
    open val email: String,
    open val role: UserRole
) : Identifiable {

    abstract fun displayUserInfo()
    abstract fun login()
    abstract fun logout()
}