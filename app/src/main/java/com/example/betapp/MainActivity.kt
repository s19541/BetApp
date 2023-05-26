package com.example.betapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.betapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.testButton.setOnClickListener{
            startActivity(Intent(this, UpcomingGamesActivity::class.java))
        }

        binding.testButton2.setOnClickListener{
            startActivity(Intent(this, UserBetsActivity::class.java))
        }

        binding.logOutButton.setOnClickListener{
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

        val user = auth.currentUser
        if (user == null) {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }

}