package screens

import model.MenuAction
import model.MenuItem
import java.util.Scanner

open class Menu {
    private val scanner = Scanner(System.`in`)

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

    fun showMenuList(menu: Map<Int, MenuItem>) {
        for (key in menu.keys) {
            val menuItem = menu[key]
            if (menuItem != null) {
                println("$key. ${menuItem.title}")
            }
        }
    }

    open fun createMenuList(): Map<Int, MenuItem> {
        return mutableMapOf()
    }

    open fun onCreateItem() { //Создание архива
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