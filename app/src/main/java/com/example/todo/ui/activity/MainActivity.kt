package com.example.todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.todo.R
import com.example.todo.ui.adapter.VP2Adapter
import com.example.todo.ui.fragment.SettingFragment
import com.example.todo.ui.fragment.TodoFragment
import com.example.todo.ui.fragment.TomatoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var navigationView: BottomNavigationView
    private lateinit var viewPager2: ViewPager2
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userId = intent.getIntExtra("userId", 0)
        val todoFragment = TodoFragment()
        val args = Bundle()
        args.putInt("userId", userId)
        todoFragment.arguments = args
        // 初始化 ViewPager2 和 BottomNavigationView
        viewPager2 = findViewById(R.id.vp2_main)
        navigationView = findViewById(R.id.navigation_main)


        Log.d("MainActivity", "接收到的用户 ID: $userId") // 打印日志确认接收到的用户 ID
        if (userId == 0) {
            // 处理 userId 获取失败的情况，例如返回登录页面
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 显式声明 Fragment 列表类型
        val fragmentList: List<Fragment> = listOf(
            TodoFragment(),
            TomatoFragment(),
            SettingFragment()
        )

        // 设置 ViewPager2 的适配器
        val adapter = VP2Adapter(this, fragmentList)
        viewPager2.adapter = adapter

        // 设置 BottomNavigationView 的选中监听器
        navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_todo -> viewPager2.currentItem = 0
                R.id.menu_focus -> viewPager2.currentItem = 1
                R.id.menu_setting -> viewPager2.currentItem = 2
            }
            true
        }

        // 设置 ViewPager2 的页面改变监听器
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                navigationView.menu.getItem(position).isChecked = true
            }
        })
    }
}
