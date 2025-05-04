package com.example.todo.model.repository

import com.example.todo.model.bean.User
import com.example.todo.model.database.AppDataBase

object LoginRepository {
    private val userDao = AppDataBase.getDatabase().userDao()
    fun checkUser(username: String, password: String) = userDao.checkUser(username, password)

    fun checkSign(username: String) = userDao.checkSign(username).map { count -> count > 0 }

    fun insertUser(user: User) = userDao.insertUser(user)
}