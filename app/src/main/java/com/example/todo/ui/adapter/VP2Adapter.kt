package com.example.todo.ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todo.ui.activity.MainActivity

class VP2Adapter(
    activity: FragmentActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(activity) {

    private val userId: Int = (activity as MainActivity).intent.getIntExtra("userId", 0)

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        val fragment = fragmentList[position]
        val args = Bundle()
        args.putInt("userId", userId)
        fragment.arguments = args
        return fragment
    }
}
