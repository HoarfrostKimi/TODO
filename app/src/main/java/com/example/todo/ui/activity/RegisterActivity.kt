package com.example.todo.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.todo.R
import com.example.todo.model.bean.User
import com.example.todo.viewmodel.LoginViewModel
import com.example.todo.viewmodel.RegisterViewModel
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity:AppCompatActivity() {
    //注册控件
    private lateinit var mEtSignUsername: EditText
    private lateinit var mEtSignPassword: EditText
    private lateinit var mEtSignRePassword: EditText
    private lateinit var mTilAccount: TextInputLayout
    private lateinit var mTilPassword: TextInputLayout
    private lateinit var mTilPassword2: TextInputLayout
    private lateinit var bt_register: Button


    private val viewmodel : RegisterViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
        initEvent()
    }
    private fun initView() {
        mEtSignUsername=findViewById(R.id.et_register_account)
        mEtSignPassword=findViewById(R.id.et_register_password)
        mEtSignRePassword=findViewById(R.id.et_register_password2)
        mTilAccount=findViewById(R.id.til_register_account)
        mTilPassword=findViewById(R.id.til_register_password)
        mTilPassword2=findViewById(R.id.til_register_password2)
        bt_register=findViewById(R.id.bt_register)

    }
    private fun initEvent() {
        mEtSignUsername.addTextChangedListener {
            if (mEtSignUsername.text.toString() != "") {
                mTilAccount.isErrorEnabled = false
            }
        }
        mEtSignPassword.addTextChangedListener {
            if (mEtSignPassword.text.toString() != "") {
                mTilPassword.isErrorEnabled = false
            }
        }
        mEtSignRePassword.addTextChangedListener {
            if (mEtSignRePassword.text.toString() != "") {
                mTilPassword2.isErrorEnabled = false
            }
        }
        var username = mEtSignUsername.text.toString()
        var password = mEtSignPassword.text.toString()
        bt_register.setOnClickListener {
            username = mEtSignUsername.text.toString()
            password = mEtSignPassword.text.toString()
            val rePassword = mEtSignRePassword.text.toString()
            if (username == "") {
                mTilAccount.setError("用户名不能为空!")
            } else {
                if (password == "") {
                    mTilPassword.setError("密码不能为空!")
                } else {
                    if (rePassword == "") {
                        mTilPassword2.setError("请再次输入密码!")
                    } else {
                        if (password != rePassword) {
                            mTilPassword2.setError("两次密码输入不相同!")
                        } else {
                            viewmodel.sign(username)
                        }
                    }
                }
            }
        }
        //TODO:编写登录逻辑
        // 监听是否已注册
        viewmodel.livedataIsSign.observe(this) { isSign ->
            if (isSign) {

                android.app.AlertDialog.Builder(this)
                    .setTitle("注册失败")
                    .setMessage("账号已经注册")
                    .setPositiveButton("确认") { dialog, _ ->
                        dialog.dismiss()
                        mEtSignUsername.setText("")
                        mEtSignPassword.setText("")
                        mEtSignRePassword.setText("")
                    }
                    .show()
            } else {
                val username = mEtSignUsername.text.toString()
                val password = mEtSignPassword.text.toString()
                viewmodel.insertUser(User(username, password))
            }
        }

        // 监听用户ID变化（注册成功）
        viewmodel.livedataUserId.observe(this) { userId ->
            if (userId > 0) {
                android.app.AlertDialog.Builder(this)
                    .setTitle("注册成功")
                    .setMessage("请返回登录")
                    .setPositiveButton("确认") { dialog, _ ->
                        dialog.dismiss()
                        finish() // 关闭当前注册页面，返回登录页
                    }
                    .setCancelable(false)
                    .show()
            }
        }


    }


}