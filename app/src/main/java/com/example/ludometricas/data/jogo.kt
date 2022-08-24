package com.example.ludometricas.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

data class Jogo (
    var id: Int = 0,
    var nome: String = "",
    var recorde: Recorde? = Recorde(),
    var tempoJogado: Long,
    var notaMediaAteOMomento: Nota = Nota(),
    var notaTotalAteOMomento: Nota = Nota(),
    var notasIndividuaisAteOMomento: List<NotaIndividual> = mutableListOf(),
    var historicoJogatinas: MutableList<Jogatina> = mutableListOf(),
    var notasTotaisIndividuais: MutableList<NotaIndividual> = mutableListOf(),
    var jogatinas: Int = 0,
    var historicoRecordes: MutableList<Recorde>? = mutableListOf(),
    var tempoMedioJogatina: Long,
    var tags: MutableList<String> = mutableListOf()

)