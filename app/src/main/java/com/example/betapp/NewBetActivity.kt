package com.example.betapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.betapp.bet.Bet
import com.example.betapp.bet.BetViewModel
import com.example.betapp.bet.Result
import com.example.betapp.databinding.ActivityNewBetBinding
import com.example.betapp.game.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter

class NewBetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewBetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewBetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val game = intent.extras?.get("game") as Game

        binding.textViewTeam1.text = game.team1
        binding.textViewTeam2.text = game.team2
        binding.textViewDateTime.text = game.dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))

        binding.imageViewTeam1.setImageResource(this.resources.getIdentifier(game.team1.replace(" ", "_").lowercase(), "drawable", this.packageName))
        binding.imageViewTeam2.setImageResource(this.resources.getIdentifier(game.team2.replace(" ", "_").lowercase(), "drawable", this.packageName))

        binding.chipWin.text = game.rateWin.toString()
        binding.chipDraw.text = game.rateDraw.toString()
        binding.chipLoss.text = game.rateLoss.toString()

        binding.buttonBet.setOnClickListener{
            if(binding.chipGroup.checkedChipId != null && binding.editTextInput.text.toString().toIntOrNull() != null){
                val betViewModel = BetViewModel(application)
                var rate: Double
                var result: Result
                if(binding.chipWin.isChecked) {
                    rate = binding.chipWin.text.toString().toDoubleOrNull() ?: 0.0
                    result = Result.W
                }
                else if(binding.chipDraw.isChecked){
                    rate = binding.chipDraw.text.toString().toDoubleOrNull() ?: 0.0
                    result = Result.D
                }
                else{
                    rate = binding.chipLoss.text.toString().toDoubleOrNull() ?: 0.0
                    result = Result.L
                }
                CoroutineScope(Dispatchers.IO).launch {
                    betViewModel.insert(
                        Bet(
                            id = "",
                            gameId = game.id,
                            rate = rate,
                            result = result,
                            input = binding.editTextInput.text.toString().toDoubleOrNull() ?: 0.0,
                            settled = false
                        )
                    )
                }
            }
            else {
                Toast.makeText(this, "You have to fill all required fields to bet", Toast.LENGTH_LONG).show()
            }

        }
    }
}