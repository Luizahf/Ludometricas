package com.example.ludometricas.data.entities

import com.example.ludometricas.data.NotaIndividual

class Avaliacao (
    var nomeJogo: String = "",
    var notaMediaAteOMomento: Double,
    var notaJogatina: Double,
    var notaMecanica: Double,
    var notaComponentes: Double,
    var notaExperiencia: Double,
    var notasIndividuais: MutableList<NotaIndividual>,
)