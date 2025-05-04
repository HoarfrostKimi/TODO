package com.example.todo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.model.bean.Todo
import com.example.todo.model.bean.User
import com.example.todo.model.repository.MainRepository
import com.example.todo.model.repository.TodoRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _livedataTodo : MutableLiveData<List<Todo>> = MutableLiveData()
    private val _livedataIsChanged : MutableLiveData<Int> = MutableLiveData()

    val livedataTodo : LiveData<List<Todo>> get() = _livedataTodo
    val livedataIsChanged : LiveData<Int> get() = _livedataIsChanged
    fun insertTodo(todo: Todo) {
        val disposable = TodoRepository.insertTodo(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it.toInt())
            }
            .doOnError {
                Log.d("zxy", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
    fun updateTodo(todo: Todo) {
        val disposable = TodoRepository.updateTodo(todo)
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
    fun finishTodo(taskId: Int) {
        val disposable = TodoRepository.finishTodo(taskId)
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
    fun deleteTodo(todo: Todo) {
        val disposable = TodoRepository.deleteTodo(todo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                _livedataIsChanged.postValue(it)
            }
            .doOnError {
                Log.d("zzx", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${it.stackTrace}")
            }
            .subscribe()
        compositeDisposable.add(disposable)
    }
    fun loadAllTodo(userId: Int) {
        val disposable = TodoRepository.loadAllTodo(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { todoList: List<Todo> ->
                    _livedataTodo.postValue(todoList)
                },
                { error: Throwable -> // 显式声明类型
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