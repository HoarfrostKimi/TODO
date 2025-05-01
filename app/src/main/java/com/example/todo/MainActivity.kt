package com.example.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var mRadioGroup: RadioGroup
    private lateinit var tab1: RadioButton
    private lateinit var tab2: RadioButton
    private lateinit var tab3: RadioButton
    private val mViews = ArrayList<View>() // 存放视图

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()

        // 对单选按钮进行监听，选中、未选中
        mRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_todo -> mViewPager.currentItem = 0
                R.id.rb_ask -> mViewPager.currentItem = 1
                R.id.rb_setting -> mViewPager.currentItem = 2

            }
        }
    }

    private fun initView() {
        // 初始化控件
        mViewPager = findViewById(R.id.viewpager)
        mRadioGroup = findViewById(R.id.rg_tab)
        tab1 = findViewById(R.id.rb_todo)
        tab2 = findViewById(R.id.rb_ask)
        tab3 = findViewById(R.id.rb_setting)


        // 加载并添加视图
        mViews.add(layoutInflater.inflate(R.layout.todo, null))
        mViews.add(layoutInflater.inflate(R.layout.ask, null))
        mViews.add(layoutInflater.inflate(R.layout.setting, null))

        mViewPager.adapter = MyViewPagerAdapter()

        // 对ViewPager监听，让分页和底部图标保持同步滑动
        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        tab1.isChecked = true
                        tab2.isChecked = false
                        tab3.isChecked = false
                    }
                    1 -> {
                        tab1.isChecked = false
                        tab2.isChecked = true
                        tab3.isChecked = false
                    }
                    2 -> {
                        tab1.isChecked = false
                        tab2.isChecked = false
                        tab3.isChecked = true
                    }
                    3 -> {
                        tab1.isChecked = false
                        tab2.isChecked = false
                        tab3.isChecked = false
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    // ViewPager适配器
    private inner class MyViewPagerAdapter :PagerAdapter() {

        override fun getCount(): Int = mViews.size

        override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(mViews[position])
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(mViews[position])
            return mViews[position]
        }
    }
}
