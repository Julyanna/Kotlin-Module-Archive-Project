package screens

import model.MenuAction
import model.Item
import model.MenuItem
import java.util.Locale
import java.util.Scanner

open class Menu() {
    private val scanner = Scanner(System.`in`)
    companion object {
        const val EXIT_MESSAGE = "Чтобы выйти из меню создания, введите q"
        const val EXIT_SIGN = "q"
    }

    open fun onStart(title: String) { //Меню выбора

        while (true) {
            println(title)
            val menuItems = createMenuList()
            showMenuList(menuItems)
            if (!scanner.hasNextInt()) {
                println("Введите цифру пункта меню")
                getString()
            } else {
                val selectedNumber = scanner.nextInt()
                getString() // Очистка буфера ввода после Чтение числового значения из ввода

                val menuItem = menuItems[selectedNumber]
                if (menuItem != null) {
                    when (menuItem.onAction) {
                        MenuAction.EXIT -> break
                        MenuAction.CREATE_ITEM -> onCreateItem()
                        MenuAction.CHOOSE_ITEM -> onChooseItem(menuItem)
                    }
                } else {
                    println("Такого пункта меню нет, введите корректный символ")
                }
            }

        }
    }

    fun <T : Item> createMenuList(itemName: String, exitName: String, items: MutableSet<T>): Map<Int, MenuItem> {
        val menu: MutableMap<Int, MenuItem> = mutableMapOf(
            0 to MenuItem("Создать $itemName", MenuAction.CREATE_ITEM)
        )
        var orderMenuItems = 1
        for (item in items) {
            menu[orderMenuItems] = MenuItem(item.getItemName(), MenuAction.CHOOSE_ITEM)
            orderMenuItems += 1
        }
        menu[orderMenuItems] = MenuItem(exitName, MenuAction.EXIT)
        return menu
    }
    open fun createMenuList(): Map<Int, MenuItem> {
        return mutableMapOf()
    }
    private fun showMenuList(menu: Map<Int, MenuItem>) {
        for (key in menu.keys) {
            val menuItem = menu[key]
            if (menuItem != null) {
                println("$key. ${menuItem.title}")
            }
        }
    }
    open fun onCreateItem() {
    }
    fun <T : Item> onCreateItem(itemName: String, items: MutableSet<T>) {
        var isExit = false
        while (!isExit) {
            println("Введите название $itemName. $EXIT_MESSAGE")
            val itemNameInput = checkNotNullNextLine("Название $itemName не может быть пустым.")
            if (itemNameInput != null) {
                if (EXIT_SIGN == itemNameInput.lowercase(Locale.getDefault())) {
                    isExit = true
                } else {
                    var isItemExist = false
                    for (item in items) {
                        if (item.getItemName() == itemNameInput) {
                            isItemExist = true
                            break
                        }
                    }
                    if (isItemExist) println("Такое название $itemName уже есть.")
                    else {
                        onCreateContinue(itemNameInput)
                        isExit = true
                    }
                }
            }
        }
    }

    open fun onCreateContinue(itemNameInput: String)  {
    }

    open fun onChooseItem(menuItem: MenuItem) {
    }

    fun checkNotNullNextLine(message: String): String? {
        val nextLine = getString()
        if (nextLine.isEmpty()) {
            println(message)
            return null
        }
        return nextLine
    }

    private fun getString(): String {
        return scanner.nextLine()
    }

}