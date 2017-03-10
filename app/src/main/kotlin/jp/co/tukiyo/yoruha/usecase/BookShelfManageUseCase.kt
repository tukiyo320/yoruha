package jp.co.tukiyo.yoruha.usecase

import jp.co.tukiyo.yoruha.element.BookShelf

class BookShelfManageUseCase {

    companion object {
        val preShelves: List<BookShelf> = listOf(
                BookShelf(3, "読んでる本", false),
                BookShelf(2, "読みたい本", false),
                BookShelf(4, "読んだ本", false)
        )
    }

}