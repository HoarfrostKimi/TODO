package com.example.todo.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todo.model.repository.LoginRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel :ViewModel(){
    private val _livedataUserId : MutableLiveData<Int> = MutableLiveData()
    private val _livedataLoginId : MutableLiveData<Int> = MutableLiveData()
    private val _livedataIsSign : MutableLiveData<Boolean> = MutableLiveData()

    val livedataUserId : LiveData<Int> get() = _livedataUserId
    val livedataLoginId : LiveData<Int> get() = _livedataLoginId
    val livedataIsSign : LiveData<Boolean> get() = _livedataIsSign
    private val compositeDisposable = CompositeDisposable()
    fun login(username: String, password: String) {
        val disposable = LoginRepository.checkUser(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ user ->
                _livedataLoginId.postValue(user.id)
            }, { throwable ->
                _livedataLoginId.postValue(-1)
                Log.d("zxy", "(${Error().stackTrace[0].run { "$fileName:$lineNumber" }}) -> ${throwable.stackTrace}")
            })
        compositeDisposable.add(disposable)
    }




}