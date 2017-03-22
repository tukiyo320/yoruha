package jp.co.tukiyo.yoruha.data.db

import com.github.gfx.android.orma.annotation.*

@Table(
        indexes = arrayOf(Index(
                unique = true,
                value = *arrayOf("volumeId", "shelfId")
        ))
)
class Book {
    @PrimaryKey(autoincrement = true)
    var id: Long = 0

    @Column(indexed = true)
    val volumeId: String

    @Column(indexed = true)
    val shelfId: Int

    constructor(
            @Setter("id") id: Long,
            @Setter("volumeId") volumeId: String,
            @Setter("shelfId") shelfId: Int) {
        this.id = id
        this.volumeId = volumeId
        this.shelfId = shelfId
    }

    constructor(volumeId: String, shelfId: Int) {
        this.volumeId = volumeId
        this.shelfId = shelfId
    }
}