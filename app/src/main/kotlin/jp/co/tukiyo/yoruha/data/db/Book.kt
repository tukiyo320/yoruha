package jp.co.tukiyo.yoruha.data.db

import com.github.gfx.android.orma.annotation.Column
import com.github.gfx.android.orma.annotation.PrimaryKey
import com.github.gfx.android.orma.annotation.Setter
import com.github.gfx.android.orma.annotation.Table

@Table
data class Book(
        @Setter("id") @PrimaryKey var id: Long,
        @Setter("volumeId") @Column var volumeId: String,
        @Setter("shelfId") @Column var shelfId: Int
)