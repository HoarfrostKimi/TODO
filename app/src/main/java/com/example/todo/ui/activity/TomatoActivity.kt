package com.example.todo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.R

class TomatoActivity : AppCompatActivity() {
    private lateinit var countdownTextView: TextView
    private lateinit var ed_btn: Button

    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tomato)

        countdownTextView = findViewById(R.id.countdownTextView)
        ed_btn=findViewById(R.id.stopBtn)
        val timeInMinutes = intent.getIntExtra("time", 25)
        val totalMillis = timeInMinutes * 60 * 1000L
        ed_btn.setOnClickListener {
            // 跳转回 MainActivity，并携带标志用于导航到番茄Fragment
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("targetFragment", "tomato")
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // 关闭当前计时器页面
        }

        startCountdown(totalMillis)
    }

    private fun startCountdown(totalMillis: Long) {
        timer = object : CountDownTimer(totalMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                countdownTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                countdownTextView.text = "00:00"
                Toast.makeText(this@TomatoActivity, "时间到啦~", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel() // 防止内存泄露
    }
}
