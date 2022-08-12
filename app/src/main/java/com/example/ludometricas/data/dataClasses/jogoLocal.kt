package com.example.ludometricas.data.dao

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ludometricas.data.Nota


@Entity(tableName = "jogos")
data class JogoLocal (
    @PrimaryKey var nome: String = "",
    var selecionado: Boolean = false,
    var RecordeResponsavel : String = "",
    var RecordePontuacao: Int = 0,
    var RecordeData: Long? = null,
    var tempoJogado: Long?  = null,
    var notaMediaAteOMomento: Double = 0.0,
    var notaMecanicaMediaAteOMomento: Double = 0.0,
    var notaComponentesMediaAteOMomento: Double = 0.0,
    var notaExperienciaMediaAteOMomento: Double = 0.0,
    var jogatinas: Int = 0,
    var tempoMedioJogatina: Long? = null,
    var notaTotalAteOMomento: Double = 0.0
)