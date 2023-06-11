package com.example.ludometricas.jogatina

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.ludometricas.data.Jogatina
import com.example.ludometricas.data.repos.JogatinaRepository
import com.example.ludometricas.data.dataClasses.JogatinaLocal
import com.example.ludometricas.data.dataClasses.NotaIndividualLocal

class JogatinaViewModel  constructor(
    application: Application,
    var jogatinaRepository: JogatinaRepository
) : AndroidViewModel(application) {

    fun selecionarJogatina(jogatina: Jogatina, callback: () -> Any) {
        jogatinaRepository.selecionarJogatina(jogatina, callback)
    }

    fun obterJogatinaSelecionada(callback: (JogatinaLocal?) -> Any) {
        jogatinaRepository.obterJogatinaSelecionada(callback)
    }

    fun obterNotasIndividuais(jogatina: JogatinaLocal, callback: (List<NotaIndividualLocal>?) -> Any) {
        jogatinaRepository.obterNotasIndividuais(jogatina, callback)
    }
}