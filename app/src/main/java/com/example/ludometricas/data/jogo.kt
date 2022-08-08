package com.example.ludometricas.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time

class Jogo (
    var nome: String = "",
    var recorde: Recorde? = Recorde(),
    var tempoJogado: Time = Time(0, 0, 0),
    var notaMediaAteOMomento: Nota = Nota(),
    var notasIndividuaisAteOMomento: List<NotaIndividual> = mutableListOf(),
    var historicoJogatinas: MutableList<Jogatina> = mutableListOf(),
    var notasTotaisIndividuais: MutableList<NotaIndividual> = mutableListOf(),
    var jogatinas: Int = 0,
    var historicoRecordes: MutableList<Recorde>? = mutableListOf(),
    var tempoMedioJogatina: Time = Time(0, 0, 0),
    var tags: MutableList<String> = mutableListOf()

)