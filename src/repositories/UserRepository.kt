package repositories

import interfaces.Searchable
import users.User

class UserRepository : Searchable<User> {

    private val users = mutableListOf<User>()

    fun addUser(user: User) {
        users.add(user)
    }

    override fun findById(id: Int): User? {
        return users.find { it.id == id }
    }

    override fun findAll(): List<User> {
        return users
    }

    fun findByEmail(email: String): User? {
        return users.find { it.email == email }
    }
}