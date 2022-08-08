package com.example.ludometricas.presentation.jogo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ludometricas.data.Jogo
import com.example.ludometricas.data.JogosRepository
import com.example.ludometricas.data.Nota
import com.example.ludometricas.data.Recorde
import java.sql.Date
import java.sql.Time

class JogoViewModel constructor(
    application: Application,
    var jogosRepository: JogosRepository
) : AndroidViewModel(application) {
    var jogoSelecionado: MutableLiveData<Jogo> = MutableLiveData()

    fun selecionarJogo(jogo: Jogo) {
        jogoSelecionado.postValue(jogo)
    }

    fun inserirJogos() {
        jogosRepository.insert(criarJogos())
    }

    fun obterJogos() { //}: List<Jogo>? {
        return jogosRepository.getAll()
    }

    fun criarJogos() : MutableList<Jogo> {
        return mutableListOf(Jogo(
            "Lume",
            Recorde("Pepeu", 116, Date(11, 5, 22)),
            Time(0, 50, 0),
            Nota(8.795, 0.0, 0.0, 0.0),
            jogatinas = 4
        ), Jogo(
            "Viticulture + Tuscany",
            Recorde("Pepeu", 44, Date(9, 3, 22)),
            Time(1, 25, 0),
            Nota(9.15, 0.0, 0.0, 0.0),
            jogatinas = 1
        ), Jogo(
            "Conquistadores de Midgard",
            Recorde("Lulu", 229, Date(8, 1, 22)),
            Time(1, 10, 0),
            Nota(9.55, 0.0, 0.0, 0.0),
            jogatinas = 4
        ))
    }

    fun obterJogo(nomeJogo: String): Jogo? {
        return Jogo() //jogosRepository.get(nomeJogo)
    }
}