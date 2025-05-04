package com.example.todo.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.ui.activity.MainActivity
import com.example.todo.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoFragment :Fragment(){
    var userId : Int = 0
    private val viewModel : MainViewModel by viewModels()
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var mainActivity : MainActivity

    private lateinit var recyclerView: RecyclerView
    private lateinit var floatButton: FloatingActionButton
    private lateinit var adapter: HomeTaskRecyclerViewAdapter

}