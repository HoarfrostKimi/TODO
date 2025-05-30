package com.example.todo.ui.adapter

interface ItemTouchMoveListener {
    /**
     * 当拖拽的时候回调
     * 可以在方法里面实现:拖拽条目并实现刷新效果
     * @param fromPosition 从什么位置拖
     * @param toPosition 到什么位置
     * @return 是否执行了move
     */
    fun onItemMove(fromPosition: Int, toPosition: Int): Boolean

    /**
     * 当条目被移除时回调
     * @param position 移除的位置
     * @return 是否执行了remove
     */
    fun onItemDissmiss(position: Int): Boolean

}