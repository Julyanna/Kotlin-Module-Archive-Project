package model

enum class MenuAction(val isCustom: Boolean) {
    CREATE_ITEM(false),
    EXIT(false),
    CHOOSE_ITEM(true)
}