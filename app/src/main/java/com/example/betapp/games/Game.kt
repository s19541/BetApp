package com.example.betapp.games

import java.time.LocalDateTime

data class Game(
    var id: String,
    var team1: String,
    var team2: String,
    var rateWin: Double,
    var rateDraw: Double,
    var rateLoss: Double,
    var competition: String,
    var dateTime: LocalDateTime

)
