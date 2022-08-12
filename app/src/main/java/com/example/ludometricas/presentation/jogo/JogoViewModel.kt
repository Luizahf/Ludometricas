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
            Recorde("Pepeu", 116, Date(2021, 11, 22)),
            Time(0, 50, 0),
            Nota(8.795, 9.2, 9.92, 8.9),
            jogatinas = 4, tempoMedioJogatina =  Time(0, 52, 0)
        ), Jogo(
            "Viticulture + Tuscany",
            Recorde("Pepeu", 44, Date(2022, 3, 9)),
            Time(1, 25, 0),
            Nota(9.15, 9.1, 9.7, 9.2),
            jogatinas = 2, tempoMedioJogatina =  Time(1, 23, 0)
        ), Jogo(
            "Conquistadores de Midgard",
            Recorde("Lulu", 229, Date(2022, 8, 2)),
            Time(1, 10, 0),
            Nota(9.55, 9.8, 10.0, 9.7),
            jogatinas = 6, tempoMedioJogatina =  Time(1, 42, 0)
        ))
    }

    fun obterJogo(nomeJogo: String): Jogo? {
        return Jogo() //jogosRepository.get(nomeJogo)
    }
}