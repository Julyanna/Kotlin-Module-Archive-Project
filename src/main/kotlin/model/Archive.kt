package model

data class Archive(val name: String) {
    val notesList: MutableSet<Note> = mutableSetOf() //При сравнении объектов класс не должен учитывать значение переменной notesList
}