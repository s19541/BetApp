package com.example.betapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betapp.databinding.ActivityUpcomingGamesBinding
import com.example.betapp.game.GameAdapter
import com.example.betapp.game.GameViewModel

class UpcomingGamesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpcomingGamesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUpcomingGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameViewModel = GameViewModel(application)

        val adapter = GameAdapter(gameViewModel)

        binding.gamesRv.layoutManager = LinearLayoutManager(this)
        binding.gamesRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.gamesRv.adapter = adapter
        gameViewModel.allGames.observe(this, androidx.lifecycle.Observer {
            it.let{
                adapter.setGames(it.values.toList().sortedWith(compareBy { it2 -> it2.dateTime }))
            }
        })

    }
}