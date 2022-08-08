package com.example.ludometricas.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class NotaIndividual (
    var responsavel: String = "",
    var nota: Nota = Nota()
)
