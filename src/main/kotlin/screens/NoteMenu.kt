package screens

import model.Archive
import model.MenuItem
import model.Note
import java.util.Locale

class NoteMenu(private val archive: Archive): Menu() {
    private var notes: MutableSet<Note> = mutableSetOf()
    init{
        notes = archive.notesList
    }
    override fun createMenuList(): Map<Int, MenuItem> {
        return super.createMenuList("заметку", "Назад", notes)
    }
    override fun onCreateItem() {
        super.onCreateItem("заметки", notes)
    }
    override fun onCreateContinue(itemNameInput: String) {
        while (true) {
            println("Введите текст заметки. $EXIT_MESSAGE")
            val itemContent =
                checkNotNullNextLine("Текст заметки не может быть пустым.")
            if (itemContent != null) {
                if (EXIT_SIGN == itemContent.lowercase(Locale.getDefault())) {
                    break
                }
                notes.add(Note(itemNameInput, itemContent))
                println("Создана заметка с именем: $itemNameInput")
                break
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
    private fun openNote(note: Note) {
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