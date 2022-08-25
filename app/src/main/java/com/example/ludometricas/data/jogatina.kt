package com.example.ludometricas.data

import java.sql.Time
import java.util.*

class Jogatina (
    var data: String? = null,
    var notasIndividuais: List<NotaIndividual> = mutableListOf(),
    var duracao: String = "0",
    var duracaoPreparacao: String = "0"
)