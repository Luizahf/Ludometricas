package com.example.ludometricas.data

import java.io.Serializable
import java.sql.Date
import java.sql.Time

class Jogo (
    nome: String = "",
    recorde: Recorde? = Recorde(),
    tempoJogado: Time = Time(0, 0, 0),
    notaMediaAteOMomento: Nota = Nota(),
    notasIndividuaisAteOMomento: List<NotaIndividual> = mutableListOf(),
    historicoJogatinas: MutableList<Jogatina> = mutableListOf(),
    notasTotaisIndividuais: MutableList<NotaIndividual> = mutableListOf(),
    jogatinas: Int = 0,
    historicoRecordes: MutableList<Recorde>? = mutableListOf(),
) {
    var nome: String = ""
    var recorde: Recorde? = null
    var tempoJogado: Time = Time(0, 0, 0)
    var notaMediaAteOMomento: Nota = Nota()
    var notasIndividuaisAteOMomento: List<NotaIndividual> = mutableListOf()
    var historicoJogatinas: MutableList<Jogatina> = mutableListOf()
    var notasTotaisIndividuais: MutableList<NotaIndividual> = mutableListOf()
    var jogatinas: Int = 0
    var historicoRecordes: MutableList<Recorde>? = mutableListOf()
}

class Recorde (
    responsavel : String = "",
    pontuacao: Int = 0,
    data: Date? = null
) {
    var responsavel: String = ""
    var pontuacao: Int = 0
    var data: Date? = null
}

class NotaIndividual (
    responsavel: String = "",
    nota: Nota = Nota()
) {
    var responsavel: String = ""
    var nota: Nota = Nota()
}

class Jogatina (
    data: Date? = null,
    notasIndividuais: List<NotaIndividual> = mutableListOf(),
    duracao: Time = Time(0, 0, 0),
    duracaoPreparacao: Time = Time(0, 0, 0)
) {
    var data: Date? = null
    var notasIndividuais: List<NotaIndividual> = mutableListOf()
    var duracao: Time = Time(0, 0, 0)
    var duracaoPreparacao: Time = Time(0, 0, 0)
}

class Nota (
    total: Double = 0.0,
    mecanica: Double = 0.0,
    componentes: Double = 0.0,
    experiencia: Double = 0.0
) {
    var total: Double = 0.0
    var mecanica: Double = 0.0
    var componentes: Double = 0.0
    var experiencia: Double = 0.0
}