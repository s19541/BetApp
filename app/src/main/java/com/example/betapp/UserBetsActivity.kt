package com.example.betapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.betapp.bet.BetAdapter
import com.example.betapp.bet.BetViewModel
import com.example.betapp.databinding.ActivityUserBetsBinding
import com.example.betapp.game.GameViewModel


class UserBetsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBetsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBetsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val betViewModel = BetViewModel(application)
        val gameViewModel = GameViewModel(application)

        val adapter = BetAdapter(betViewModel, gameViewModel)

        binding.betsRv.layoutManager = LinearLayoutManager(this)
        binding.betsRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        binding.betsRv.adapter = adapter
        betViewModel.allBets.observe(this, androidx.lifecycle.Observer {
            it.let{
                adapter.setBets(it.values.toList())
            }
        })
        gameViewModel.allGames.observe(this, androidx.lifecycle.Observer {
            it.let{
                adapter.setGames(it.values.toList())
            }
        })

    }
}