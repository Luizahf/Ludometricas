package com.example.ludometricas.data

data class Jogo (
    var id: Int = 0,
    var nome: String = "",
    var recorde: Recorde? = Recorde(),
    var tempoJogado: String = "0",
    var notaMediaAteOMomento: Nota = Nota(),
    var notaTotalAteOMomento: Nota = Nota(),
    var notasTotaisIndividuais: List<NotaIndividual> = mutableListOf(),
    var historicoJogatinas: MutableList<Jogatina> = mutableListOf(),
    var jogatinas: Int = 0,
    var historicoRecordes: MutableList<Recorde> = mutableListOf(),
    var tempoMedioJogatina: String = "0",
    var tempoMedioPreparacao: String = "0",
    var tags: MutableList<String> = mutableListOf(),
    var posicaoNota: Int = 0,
    var mecanicas: List<String> = mutableListOf()
)