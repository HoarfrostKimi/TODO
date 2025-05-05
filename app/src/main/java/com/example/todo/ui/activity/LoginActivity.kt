package com.example.todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.todo.R
import com.example.todo.ui.fragment.TodoFragment
import com.example.todo.viewmodel.LoginViewModel
import com.google.android.material.textfield.TextInputLayout

class LoginActivity:AppCompatActivity() {
    //登录控件
    private lateinit var mEtAccount: EditText
    private lateinit var mEtPassword: EditText
    private lateinit var mTilAccount: TextInputLayout
    private lateinit var mTilPassword: TextInputLayout
    private lateinit var mBtnLogin: Button
    private lateinit var mBtnSign: Button


    private val viewmodel : LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
        initEvent()

    }

    private fun initView() {
        mEtAccount=findViewById(R.id.et_login_account)
        mEtPassword=findViewById(R.id.et_login_password)
        mTilAccount=findViewById(R.id.til_login_account)
        mTilPassword=findViewById(R.id.til_login_password)
        mBtnLogin=findViewById(R.id.bt_login)
        mBtnSign=findViewById(R.id.bt_register)

    }
    private fun initEvent() {
        mEtAccount.addTextChangedListener {
            if (mEtAccount.text.toString() != "") {
                mTilAccount.isErrorEnabled = false
            }
        }
        mEtPassword.addTextChangedListener {
            if (mEtPassword.text.toString() != "") {
                mTilPassword.isErrorEnabled = false
            }
        }
        mBtnLogin.setOnClickListener {
            if (mEtAccount.text.toString() == "") {
                mTilAccount.setError("账户名不能为空")
            } else {
                if (mEtPassword.text.toString() == "") {
                    mTilPassword.setError("密码不能为空")
                } else {
                    val username = mEtAccount.text.toString()
                    val password = mEtPassword.text.toString()
                    viewmodel.login(username, password)
                }
            }
        }
        //TODO:实现登录操作
        // LoginActivity.kt
        viewmodel.livedataLoginId.observe(this) { userId ->
            if (userId == -1) {
                // 登录失败处理
                android.app.AlertDialog.Builder(this)
                    .setTitle("登录失败")
                    .setMessage("账号/密码错误")
                    .setPositiveButton("确认") { dialog, _ ->
                        dialog.dismiss()
                        clearInput()
                    }
                    .show()
            } else {
                // 登录成功处理
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                intent.putExtra("userId", userId)

                Log.d("LoginActivity", "传递的用户 ID: $userId") // 打印日志确认传递的用户 ID
                startActivity(intent)
                finish()
            }
        }


        //实现注册操作
        mBtnSign.setOnClickListener {
            register()
        }

    }

    private fun register() {
        val intent= Intent(this,RegisterActivity::class.java)
        startActivity(intent)

    }
    private fun clearInput() {
        mEtAccount.setText("")
        mEtPassword.setText("")
    }


}