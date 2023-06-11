package com.example.ludometricas.presentation.jogo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.ludometricas.data.*
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.data.dataClasses.Mecanica
import com.example.ludometricas.data.entities.Avaliacao
import com.example.ludometricas.data.repos.JogosRepository
import com.example.ludometricas.data.repos.MecanicasRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JogoViewModel constructor(
    application: Application,
    var jogosRepository: JogosRepository,
    var mecanicasRepository: MecanicasRepository
) : AndroidViewModel(application) {
    var jogos: MutableLiveData<MutableList<Jogo>> = MutableLiveData()

    fun selecionarJogo(jogo: Jogo, callback: () -> Any) {
        jogosRepository.selecionarJogo(jogo, callback)
    }
    fun deselecionarJogo() {
        jogosRepository.deselecionarJogo()
    }

    fun obterJogoSelecionado(callback: (JogoLocal?) -> Any) {
        jogosRepository.obterSelecionado { callback(it) }
    }

    fun updateJogo(jogo: JogoLocal) {
        jogosRepository.updateLocal(jogo)
    }

    fun obterJogos(callback: ((List<Jogo>) -> Any)?) {
        return jogosRepository.getAll(fun (jogosRetorno) {
            jogos.postValue(jogosRetorno.toMutableList())
            if (callback != null){
                callback(jogosRetorno)
            }
        })
    }

    fun inserirNovoJogo(nome: String, callback: (sucesso: Boolean) -> Any) {
        jogosRepository.getNewId {
            jogosRepository.insert(Jogo(it, nome), callback)
        }
    }

    fun avaliar(a: Avaliacao, callback: () -> Any) {
        return jogosRepository.avaliar(a, callback)
    }

    fun updateNome(jogo: JogoLocal, novoNome: String) {
        jogosRepository.updateLocalNome(jogo, novoNome)
    }

    fun obtecrHistoricoJogatinas(callback: (List<Jogatina>) -> Any) {
        obterJogoSelecionado {
            jogosRepository.getJogatinasLocal(it!!, callback)
        }
    }

    @DelicateCoroutinesApi
    fun obterMecanicas(nomeJogo: String, callback: (List<Mecanica>?) -> Any) {
        GlobalScope.launch {
            mecanicasRepository.get(nomeJogo, callback)
        }
    }

    @DelicateCoroutinesApi
    fun inserirMecanica(nomeJogo: String, nomeMecanica: String){
        mecanicasRepository.insert(Mecanica(nomeJogo = nomeJogo, nome = nomeMecanica))
    }
}