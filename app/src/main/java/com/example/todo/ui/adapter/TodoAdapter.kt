package com.example.todo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.model.bean.Todo

class TodoAdapter(private val todoList: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.ViewHolder>(), ItemTouchMoveListener {
    interface OnItemClickListener {
        fun onCheckStatusChanged(todo: Todo)
        fun onTodoDelete(todo: Todo)
    }
    fun getTodoList(): MutableList<Todo> {
        return todoList
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val story: TextView = view.findViewById(R.id.item_story)
        val checkBox: CheckBox = view.findViewById(R.id.checkBox)

        init {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = todoList[position]
                    itemClickListener?.onCheckStatusChanged(task)

                    // 更新 UI（示例）
                    if (isChecked) {
                        title.text = HtmlCompat.fromHtml("<s>${task.title}</s>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                        story.text = HtmlCompat.fromHtml("<s>${task.desc}</s>", HtmlCompat.FROM_HTML_MODE_LEGACY)
                        val position = adapterPosition
                        if (position != RecyclerView.NO_POSITION) {
                            moveItemToLast(position)
                        }
                    } else {
                        title.text = task.title
                        story.text = task.desc
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_main_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todoList[position]
        holder.title.text = todo.title
        holder.story.text = todo.desc
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        todoList.add(toPosition, todoList.removeAt(fromPosition))
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDissmiss(position: Int): Boolean {
        val todo = todoList[position]
        todoList.removeAt(position)
        notifyItemRemoved(position)
        itemClickListener?.onTodoDelete(todo)
        return true
    }
    fun moveItemToLast(fromPosition: Int) {
        if (fromPosition < todoList.size - 1) {
            val item = todoList.removeAt(fromPosition)
            todoList.add(item)
            notifyItemMoved(fromPosition, todoList.size - 1)
        }
    }
}