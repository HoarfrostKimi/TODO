package com.example.todo.model.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.todo.model.bean.User
import com.example.todo.model.bean.Todo
import com.example.todo.utils.Base

@Database(version = 1, entities = [User::class, Todo::class])
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao() : UserDao

    companion object {
        private var instance: AppDataBase? = null
        @Synchronized
        fun getDatabase(): AppDataBase {
            val context = Base.context
            instance?.let {
                return it
            }
            return  Room.databaseBuilder(context.applicationContext, AppDataBase::class.java, "app_database")
                .build().apply {
                    instance = this
                }
        }
    }

}