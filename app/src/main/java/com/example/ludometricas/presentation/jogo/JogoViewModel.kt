package com.example.ludometricas.presentation.jogo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ludometricas.data.*
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
            7, "As Viagens de Marco Polo"
        ),
            Jogo(
                1, "Wingspan", notaMediaAteOMomento = Nota(8.62, 9.15, 10.0, 8.75),
                notaTotalAteOMomento = Nota(8.62, 9.15, 10.0, 8.75),
                notasIndividuaisAteOMomento = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(8.14, 9.3, 10.0, 8.7), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(9.1, 9.0, 10.0, 8.8), java.util.Date())),
                notasTotaisIndividuais = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(8.14, 9.3, 10.0, 8.7), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(9.1, 9.0, 10.0, 8.8), java.util.Date())
                ), jogatinas = 1),
            Jogo(
                2, "Ruínas Perdidas de Arnak", notaMediaAteOMomento = Nota(8.88, 8.4, 9.9, 8.75),
                notaTotalAteOMomento = Nota(8.88, 8.4, 9.9, 8.75),
                notasIndividuaisAteOMomento = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(9.1, 8.8, 9.8, 9.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(8.64, 8.0, 10.0, 8.5), java.util.Date())),
                notasTotaisIndividuais = mutableListOf(
                    NotaIndividual(responsavel = "Lulu",  nota = Nota(9.1, 8.8, 9.8, 9.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(8.64, 8.0, 10.0, 8.5), java.util.Date())
                ), jogatinas = 1),

            Jogo(
                3, "Terraforming Mars", notaMediaAteOMomento = Nota(9.82, 9.6, 9.7, 10.0),
                notaTotalAteOMomento = Nota(9.82, 9.6, 9.7, 10.0),
                notasIndividuaisAteOMomento = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(9.79, 9.7, 9.4, 10.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(9.85, 9.5, 10.0, 10.0), java.util.Date())),
                notasTotaisIndividuais = mutableListOf(
                    NotaIndividual(responsavel = "Lulu",  nota = Nota(9.79, 9.7, 9.4, 10.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(9.85, 9.5, 10.0, 10.0), java.util.Date())
                ), jogatinas = 1),
            Jogo(
                4, "Great Western Trail", notaMediaAteOMomento = Nota(9.05, 9.4, 9.25, 9.25),
                notaTotalAteOMomento = Nota(9.05, 9.4, 9.25, 9.25),
                notasIndividuaisAteOMomento = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(9.25, 9.5, 9.5, 9.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(9.34, 9.3, 9.0, 9.5), java.util.Date())),
                notasTotaisIndividuais = mutableListOf(
                    NotaIndividual(responsavel = "Lulu",  nota = Nota(9.25, 9.5, 9.5, 9.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(9.34, 9.3, 9.0, 9.5), java.util.Date())
                ), jogatinas = 1),
            Jogo(
                5, "Lume", notaMediaAteOMomento = Nota(8.795, 8.5, 9.15, 8.75),
                notaTotalAteOMomento = Nota(8.795, 8.5, 9.15, 8.75),
                notasIndividuaisAteOMomento = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(8.91, 8.5, 9.3, 9.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(8.6, 8.5, 9.0, 8.5), java.util.Date())),
                notasTotaisIndividuais = mutableListOf(
                    NotaIndividual(responsavel = "Lulu",  nota = Nota(8.91, 8.5, 9.3, 9.0), java.util.Date()),
                    NotaIndividual("Pepeu",  Nota(8.6, 8.5, 9.0, 8.5), java.util.Date())
                ), jogatinas = 1),
            Jogo(
                6, "Discoveries", notaMediaAteOMomento = Nota(8.585, 8.6, 9.4, 8.25),
                notaTotalAteOMomento = Nota(8.585, 8.6, 9.4, 8.25),
                notasIndividuaisAteOMomento = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(8.51, 8.2, 9.0, 8.5), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(8.66, 9.0, 9.8, 8.0), java.util.Date())),
                notasTotaisIndividuais = mutableListOf(
                    NotaIndividual(responsavel = "Lulu",  nota = Nota(8.51, 8.2, 9.0, 8.5), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(8.66, 9.0, 9.8, 8.0), java.util.Date())
                ), jogatinas = 1),
            Jogo(
                0, "Teste", notaMediaAteOMomento = Nota(0.0, 0.0, 0.0, 0.0),
                notaTotalAteOMomento = Nota(0.0, 0.0, 0.0, 0.0),
                notasIndividuaisAteOMomento = mutableListOf(
                    NotaIndividual(responsavel = "Lulu", nota = Nota(0.0, 0.0, 0.0, 0.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(0.0, 0.0, 0.0, 0.0), java.util.Date())),
                notasTotaisIndividuais = mutableListOf(
                    NotaIndividual(responsavel = "Lulu",  nota = Nota(0.0, 0.0, 0.0, 0.0), java.util.Date()),
                    NotaIndividual("Pepeu", Nota(0.0, 0.0, 0.0, 0.0), java.util.Date())
                ), jogatinas = 1)
        )
    }

    fun avaliar(a: Avaliacao) {
        return jogosRepository.avaliar(a)
    }
}