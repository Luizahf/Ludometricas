package com.example.ludometricas.data

import java.io.Serializable

class Jogo : Serializable {
    var nome: String = ""
    var nota: Double = 0.0
    var pontuacaMedia: Double = 0.0
    var recorde: Int = 0
    var tempoMedio: Int = 0
}