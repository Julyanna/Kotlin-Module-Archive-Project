package model

data class Note(val name: String, val content: String): Item {
    override fun getItemName(): String {
        return name
    }
}
