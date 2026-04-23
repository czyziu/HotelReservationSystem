package interfaces

interface Searchable<T> {
    fun findById(id: Int): T?
    fun findAll(): List<T>
}