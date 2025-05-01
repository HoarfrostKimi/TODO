package com.example.todo

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NoSwipeableViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    // 是否允许滑动
    private var isSwipeEnabled = false

    // 拦截触摸事件
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isSwipeEnabled && super.onInterceptTouchEvent(event)
    }

    // 处理触摸事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isSwipeEnabled && super.onTouchEvent(event)
    }

    // 提供一个方法用于动态开启/关闭滑动
    fun setSwipeEnabled(enabled: Boolean) {
        isSwipeEnabled = enabled
    }
}
