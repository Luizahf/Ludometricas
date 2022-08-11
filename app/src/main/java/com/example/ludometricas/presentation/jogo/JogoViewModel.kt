package com.example.ludometricas.presentation.jogo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ludometricas.data.Jogo
import com.example.ludometricas.data.JogosRepository
import com.example.ludometricas.data.Nota
import com.example.ludometricas.data.Recorde
import com.example.ludometricas.data.dao.JogoLocal
import java.sql.Date
import java.sql.Time

class JogoViewModel constructor(
    application: Application,
    var jogosRepository: JogosRepository
) : AndroidViewModel(application) {
    var jogos: MutableLiveData<MutableList<Jogo>> = MutableLiveData()

    fun selecionarJogo(jogo: Jogo) {
        jogosRepository.selecionarJogo(jogo)
    }

    fun obterJogoSelecionado(callback: (JogoLocal?) -> Any) {
        jogosRepository.obterSelecionado { callback(it) }
    }

    fun inserirJogos() {
        jogosRepository.insert(criarJogos())
    }

    fun obterJogos() {
        return jogosRepository.getAll(fun (jogosRetorno) {
            jogos.postValue(jogosRetorno.toMutableList())
        })
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