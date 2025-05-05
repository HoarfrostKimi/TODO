package com.example.todo.viewmodel

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.model.bean.Todo
import com.example.todo.model.repository.TodoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _livedataTodo: MutableLiveData<List<Todo>> = MutableLiveData()
    private val _livedataIsChanged: MutableLiveData<Int> = MutableLiveData()

    val livedataTodo: LiveData<List<Todo>> get() = _livedataTodo
    val livedataIsChanged: LiveData<Int> get() = _livedataIsChanged

    // MainViewModel.kt
    fun insertTodo(todo: Todo, userId: Int) {
        todo.userId = userId
        Log.d("MainViewModel", "插入待办事项的用户 ID: $userId") // 打印日志确认设置的用户 ID
        val disposable = TodoRepository.insertTodo(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .doOnSuccess {
                Handler(Looper.getMainLooper()).post {
                    _livedataIsChanged.postValue(it.toInt())
                }
            }
            .doOnError {
                Handler(Looper.getMainLooper()).post {
                    println("(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
                }
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    fun deleteTodo(todo: Todo) {
        val disposable = TodoRepository.deleteTodo(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .doOnError {
                Log.d("zxy", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }

    // MainViewModel.kt
    fun loadAllTodo(userId: Int) {
        Log.d("MainViewModel", "加载待办事项的用户 ID: $userId") // 打印日志确认传递的用户 ID
        val disposable = TodoRepository.loadAllTodo(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { todoList: List<Todo> ->
                    _livedataTodo.postValue(todoList)
                },
                { error: Throwable ->
                    Log.e("MainViewModel", "加载 Todo 列表失败", error)
                    _livedataTodo.postValue(emptyList())
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}