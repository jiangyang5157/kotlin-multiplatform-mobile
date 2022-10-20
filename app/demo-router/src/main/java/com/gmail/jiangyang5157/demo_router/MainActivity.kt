package com.gmail.jiangyang5157.demo_router

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gmail.jiangyang5157.demo_router.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.btn1.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    com.gmail.jiangyang5157.demo_router.fragmentroute.ExampleActivity::class.java
                )
            )
        }

        binding.btn2.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    com.gmail.jiangyang5157.demo_router.keyroute.uri.ExampleActivity::class.java
                )
            )
        }

        binding.btn3.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    com.gmail.jiangyang5157.demo_router.keyroute.uri.ExampleCustomRouteStorageActivity::class.java
                )
            )
        }

        binding.btn4.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    com.gmail.jiangyang5157.demo_router.keyroute.uri.ExampleCustomStackStorageActivity::class.java
                )
            )
        }
    }
}
