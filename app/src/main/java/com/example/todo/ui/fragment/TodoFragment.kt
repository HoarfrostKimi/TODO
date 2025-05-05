package com.example.todo.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.bean.Todo
import com.example.todo.ui.adapter.MyItemTouchHelperCallback
import com.example.todo.ui.adapter.TodoAdapter
import com.example.todo.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TodoFragment : Fragment() {
    var userId: Int = 0
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var floatButton: FloatingActionButton
    private lateinit var adapter: TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getInt("userId", 0)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_todo, container, false)
        recyclerView = view.findViewById(R.id.rv_todo)
        floatButton = view.findViewById(R.id.floatingActionButton)

        setupRecyclerView()
        setupFab()
        Log.d("fragment","传入的为"+userId)
        viewModel.loadAllTodo(userId)
        viewModel.livedataTodo.observe(viewLifecycleOwner) { todoList ->
            adapter.getTodoList().clear()
            adapter.getTodoList().addAll(todoList)
            adapter.notifyDataSetChanged()
        }

        return view
    }

    private fun setupRecyclerView() {
        adapter = TodoAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val itemTouchHelperCallback = MyItemTouchHelperCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        adapter.itemClickListener = object : TodoAdapter.OnItemClickListener {
            override fun onCheckStatusChanged(todo: Todo) {
                // 处理任务状态改变
            }

            override fun onTodoDelete(todo: Todo) {
                viewModel.deleteTodo(todo)
            }
        }
    }

    private fun setupFab() {
        floatButton.setOnClickListener {
            showAddTaskDialog()
        }
    }

    private fun showAddTaskDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_task, null)
        val titleInput = dialogView.findViewById<TextInputEditText>(R.id.et_task_title)
        val descInput = dialogView.findViewById<TextInputEditText>(R.id.et_task_desc)
        val titleLayout = dialogView.findViewById<TextInputLayout>(R.id.til_task_title)
        val descLayout = dialogView.findViewById<TextInputLayout>(R.id.til_task_desc)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("添加任务")
            .setView(dialogView)
            .setPositiveButton("添加") { _, _ ->
                val title = titleInput.text.toString().trim()
                val desc = descInput.text.toString().trim()
                if (title.isNotEmpty()) {
                    // 补充 top 参数，这里假设默认为 0
                    val todo = Todo(title = title, desc = desc, userId = userId, top = 0)
                    viewModel.insertTodo(todo, userId)
                } else {
                    titleLayout.error = "标题不能为空"
                }
            }
            .setNegativeButton("取消", null)
            .show()
    }
}