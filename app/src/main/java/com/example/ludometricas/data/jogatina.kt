package com.example.ludometricas.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

class Jogatina (
    var data: Date? = null,
    var notasIndividuais: List<NotaIndividual> = mutableListOf(),
    var duracao: Time = Time(0, 0, 0),
    var duracaoPreparacao: Time = Time(0, 0, 0)
)