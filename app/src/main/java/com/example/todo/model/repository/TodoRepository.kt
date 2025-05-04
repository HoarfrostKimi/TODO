package com.example.todo.model.repository

import com.example.todo.model.bean.Todo
import com.example.todo.model.database.AppDataBase

object TodoRepository {
    private val userDao = AppDataBase.getDatabase().userDao()
    fun insertTodo(todo: Todo) = userDao.insertTODO(todo)
    fun updateTodo(todo: Todo) = userDao.updateTODO(todo)
    fun finishTodo(taskId: Int) = userDao.finishTodo(taskId)
    fun deleteTodo(todo:Todo)= userDao.deleteTodo(todo)
    fun loadAllTodo(userId: Int) = userDao.loadAllTodo(userId)

}