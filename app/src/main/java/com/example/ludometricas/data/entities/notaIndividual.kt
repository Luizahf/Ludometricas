package com.example.ludometricas.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class NotaIndividual (
    var responsavel: String = "",
    var nota: Nota = Nota(),
    var data: String
)
