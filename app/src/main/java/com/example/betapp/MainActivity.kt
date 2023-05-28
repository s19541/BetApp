package com.example.betapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.betapp.databinding.ActivityMainBinding
import com.example.betapp.userData.UserDataViewModel
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(auth.currentUser != null){
            val userDataViewModel = UserDataViewModel(application)

            userDataViewModel.userData.observe(this, androidx.lifecycle.Observer {
                it.let{
                    supportActionBar?.subtitle = "Current points: " + it.points.toString()
                }
            })


        }



        binding.testButton.setOnClickListener{
            startActivity(Intent(this, UpcomingGamesActivity::class.java))
        }

        binding.testButton2.setOnClickListener{
            startActivity(Intent(this, UserBetsActivity::class.java))
        }

        binding.testButton3.setOnClickListener{
            startActivity(Intent(this, UserBetsHistoryActivity::class.java))
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.custom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logoutMenu){
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
        return true
    }

}