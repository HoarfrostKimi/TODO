package com.example.todo.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    var title: String,
    var desc: String,
    @ColumnInfo(name = "user_id")
    var userId: Int,
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val top: Int // 添加 top 字段
){
    //完成为0,未完成为1
    var finish: Int = 0
}
