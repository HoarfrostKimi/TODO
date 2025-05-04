package com.example.todo.model.repository

import com.example.todo.model.database.AppDataBase

object MainRepository {
    private val userDao = AppDataBase.getDatabase().userDao()

    fun getUser(userId: Int) = userDao.getUser(userId)
}