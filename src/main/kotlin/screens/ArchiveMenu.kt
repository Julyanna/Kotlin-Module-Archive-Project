package screens

import model.Archive
import model.MenuAction
import model.MenuItem
import java.util.Locale

class ArchiveMenu(): Menu() {

    private val archives: MutableSet<Archive> = mutableSetOf()

    override fun onStart(title: String){
        super.onStart("Список архивов:")
        archives.clear()
    }

    override fun createMenuList(): Map<Int, MenuItem> {
        val menu: MutableMap<Int, MenuItem> = mutableMapOf(
            0 to MenuItem("Создать архив", MenuAction.CREATE_ITEM)
        )
        var orderMenuItems = 1
        for (archive in archives) {
            menu.put(orderMenuItems, MenuItem(archive.name, MenuAction.CHOOSE_ITEM))
            orderMenuItems += 1
        }
        menu.put(orderMenuItems, MenuItem("Выход", MenuAction.EXIT))
        return menu
    }

    override fun onCreateItem() { //Создание архива
        val exitSign = "q"
        val exitMessage = "Чтобы выйти из меню создания, введите q"
        while (true) {
            println("Введите название архива. $exitMessage")
            val archiveName = checkNotNullNextLine("Название архива не может быть пустым.")
            if (archiveName != null) {
                if (exitSign == archiveName.lowercase(Locale.getDefault())) {
                    break
                }
                var isArchiveExist = false
                for (archive in archives) {
                    if (archive.name == archiveName) {
                        isArchiveExist = true
                        break
                    }
                }
                if (isArchiveExist) println("Такое название архива уже есть.")
                else {
                    archives.add(Archive(archiveName))
                    println("Создан архив с именем: $archiveName")
                    break
                }
            }
        }
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