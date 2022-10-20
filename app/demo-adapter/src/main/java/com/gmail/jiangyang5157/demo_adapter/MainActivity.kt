package com.gmail.jiangyang5157.demo_adapter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.jiangyang5157.demo_adapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRv.setOnClickListener {
            startActivity(Intent(this, RecycleViewActivity::class.java))
        }

        binding.btnSv.setOnClickListener {
            startActivity(Intent(this, ScrollViewActivity::class.java))
        }
    }
}
