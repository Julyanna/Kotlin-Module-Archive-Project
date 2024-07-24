package model

data class Archive(val name: String): Item {
    val notesList: MutableSet<Note> = mutableSetOf() //При сравнении объектов класс не должен учитывать значение переменной notesList
    override fun getItemName(): String {
        return name
    }
}