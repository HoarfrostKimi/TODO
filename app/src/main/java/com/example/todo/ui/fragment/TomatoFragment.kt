package com.example.todo.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.todo.R



import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.todo.ui.activity.MainActivity
import com.example.todo.ui.activity.TomatoActivity
import com.example.todo.viewmodel.MainViewModel

class TomatoFragment : Fragment() {
    private val mainViewModel : MainViewModel by activityViewModels()
    private lateinit var mainActivity: MainActivity
    private lateinit var mBtn1: Button
    private lateinit var mBtn25: Button
    private lateinit var mBtn40: Button
    private lateinit var mBtn_free: Button
    private lateinit var ed_text: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 这里可以加载布局
        return inflater.inflate(R.layout.fragment_tomato, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBtn1 = view.findViewById(R.id.StartBtn_1m)
        mBtn25 = view.findViewById(R.id.StartBtn_25m)
        mBtn40 = view.findViewById(R.id.StartBtn_40m)
        mBtn_free = view.findViewById(R.id.StartBtn_cus)
        ed_text=view.findViewById(R.id.timeEditText)

        mBtn1.setOnClickListener {
            val intent = Intent(requireContext(), TomatoActivity::class.java)
            intent.putExtra("time", 1)
            startActivity(intent)
        }

        mBtn25.setOnClickListener {
            val intent = Intent(requireContext(), TomatoActivity::class.java)
            intent.putExtra("time", 25)
            startActivity(intent)
        }

        mBtn40.setOnClickListener {
            val intent = Intent(requireContext(), TomatoActivity::class.java)
            intent.putExtra("time", 40)
            startActivity(intent)
        }

        mBtn_free.setOnClickListener {
            val timeStr = ed_text.text.toString()
            val time = timeStr.toIntOrNull() ?: 25 // 尝试转为 Int，失败就默认 25
            val intent = Intent(requireContext(), TomatoActivity::class.java)
            intent.putExtra("time", time)
            startActivity(intent)
        }

    }



}

