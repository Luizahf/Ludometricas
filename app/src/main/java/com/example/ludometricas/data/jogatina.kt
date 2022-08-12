package com.example.ludometricas.data

import java.sql.Time
import java.util.*

class Jogatina (
    var data: Date? = null,
    var notasIndividuais: List<NotaIndividual> = mutableListOf(),
    var duracao: Time = Time(0, 0, 0),
    var duracaoPreparacao: Time = Time(0, 0, 0)
)