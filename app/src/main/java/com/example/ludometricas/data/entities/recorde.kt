package com.example.ludometricas.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

class Recorde (
    var responsavel : String = "",
    var pontuacao: Int = 0,
    var data: String? = null
)