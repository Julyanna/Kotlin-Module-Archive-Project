package screens

import model.Archive
import model.MenuAction
import model.MenuItem
import model.Note
import java.util.Locale

class NoteMenu(private val archive: Archive): Menu() {
    private var notes: MutableSet<Note> = mutableSetOf()

    init{
        notes = archive.notesList
    }

    override fun createMenuList(): Map<Int, MenuItem> {
        val menu: MutableMap<Int, MenuItem> = mutableMapOf(
            0 to MenuItem("Создать заметку", MenuAction.CREATE_ITEM)
        )
        var orderMenuItems = 1
        for (note in notes) {
            menu[orderMenuItems] = MenuItem(note.name, MenuAction.CHOOSE_ITEM)
            orderMenuItems += 1
        }
        menu[orderMenuItems] = MenuItem("Вернуться", MenuAction.EXIT)
        return menu
    }

    override fun onCreateItem() { //Создание заметки
        val exitSign = "q"
        val exitMessage = "Чтобы выйти из меню создания, введите q"
        var isExit = false
        while (!isExit) {
            println("Введите название заметки. $exitMessage")
            val itemName = checkNotNullNextLine("Название заметки не может быть пустым.")
            if (itemName != null) {
                if (exitSign == itemName.lowercase(Locale.getDefault())) {
                    isExit = true
                } else {
                    var isArchiveExist = false
                    for (note in notes) {
                        if (note.name == itemName) {
                            isArchiveExist = true
                            break
                        }
                    }
                    if (isArchiveExist) println("Такое название заметки уже есть.")
                    else {
                        while (true) {
                            println("Введите текст заметки. $exitMessage")
                            val itemContent =
                                checkNotNullNextLine("Текст заметки не может быть пустым.")
                            if (itemContent != null) {
                                if (exitSign == itemContent.lowercase(Locale.getDefault())) {
                                    isExit = true
                                    break
                                }
                                notes.add(Note(itemName, itemContent))
                                println("Создана заметка с именем: $itemName")
                                isExit = true
                                break
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onChooseItem(menuItem: MenuItem) {
        for (note in notes) {
            if (note.name == menuItem.title) {
                openNote(note)
                break
            }
        }
    }

    fun openNote(note: Note) {
        val isExit = false
        val exitSign = "q"
        while(!isExit) {
            println("Название заметки: ${note.name}")
            println("Текст заметки: ${note.content}")
            println("Чтобы выйти из меню просмотра, введите q")
            val itemName = checkNotNullNextLine("Вы ничего не ввели")
            if (itemName != null) {
                if (exitSign == itemName.lowercase(Locale.getDefault())) {
                    break
                }
            }
        }

    }
}