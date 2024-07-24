package screens

import model.Archive
import model.MenuItem

class ArchiveMenu(): Menu() {
    private val archives: MutableSet<Archive> = mutableSetOf()

    override fun onStart(title: String){
        super.onStart("Список архивов:")
        archives.clear()
    }

    override fun createMenuList(): Map<Int, MenuItem> {
        return super.createMenuList("архив", "Выход", archives)
    }

    override fun onCreateItem() {
        super.onCreateItem("архива", archives)
    }

    override fun onCreateContinue(itemNameInput: String) {
        archives.add(Archive(itemNameInput))
        println("Создан архив с именем: $itemNameInput")
    }

    override fun onChooseItem(menuItem: MenuItem) {
        for (archive in archives) {
            if (archive.name == menuItem.title) {
                val noteMenu = NoteMenu(archive)
                noteMenu.onStart("Список заметок архива ${archive.name}:")
                break
            }
        }
    }

}