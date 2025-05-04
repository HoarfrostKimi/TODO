package com.example.todo.model.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todo.model.bean.Todo
import com.example.todo.model.bean.User
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

@Dao
interface UserDao {
    //添加用户
    @Insert
    fun insertUser(user: User) : Single<Long>
    //检查账号密码
    @Query("select * from users where username = :username and password = :password")
    fun checkUser(username: String, password: String) : Single<User>

    //判断账户是否存在
    @Query("select count(*) from users where username = :username")
    fun checkSign(username: String) : Single<Int>

    @Query("select * from users where id = :userId")
    fun getUser(userId: Int) : Single<User>
    //添加todo事项
    @Insert
    fun insertTODO(todo:Todo):Single<Long>
    //更新todo事项
    @Update
    fun updateTODO(todo:Todo) : Single<Int>
    //更新todo的完成状态
    @Query("update todo set finish = case when finish = 0 then 1 else 0 end where id = :taskId")
    fun finishTodo(taskId: Int) : Single<Int>
    //删除todo
    @Delete
    fun deleteTodo(todo:Todo) : Single<Int>
    //查找所有todo
    @Query("select * from TODO  where user_id = :userId order by  top, finish, id")
    fun loadAllParentTask(userId: Int) : Flowable<List<Todo>>


}