package services

import exceptions.UserNotFoundException
import repositories.UserRepository
import users.User

class AuthService(
    private val userRepository: UserRepository
) {

    fun loginByEmail(email: String): User {
        return userRepository.findByEmail(email)
            ?: throw UserNotFoundException("User with email $email not found.")
    }
}