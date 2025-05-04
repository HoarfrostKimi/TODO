package com.example.todo.utils

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class Base():Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()

        //获取全局context
        context = applicationContext
    }
}