package com.example.ludometricas.data

data class Jogatina (
    var data: String? = null,
    var notasIndividuais: List<NotaIndividual> = mutableListOf(),
    var duracao: String = "0",
    var duracaoPreparacao: String = "0"
)