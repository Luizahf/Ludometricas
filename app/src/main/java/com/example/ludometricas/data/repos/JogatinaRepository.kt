package com.example.ludometricas.data.repos

import com.example.ludometricas.data.Jogatina
import com.example.ludometricas.data.dao.JogatinasDao
import com.example.ludometricas.data.dao.NotasIndividuaisDao
import com.example.ludometricas.data.dataClasses.JogatinaLocal
import com.example.ludometricas.data.dataClasses.NotaIndividualLocal
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JogatinaRepository(var repo : JogatinasDao, var notasRepo: NotasIndividuaisDao) {
    fun selecionarJogatina(jogatina: Jogatina, callback: () -> Any) {
        GlobalScope.launch {
            repo.deleteAll()
            repo.insert(JogatinaLocal(data = jogatina.data, duracaoPreparacao = jogatina.duracaoPreparacao, duracao = jogatina.duracao))
            notasRepo.deleteAll()
            jogatina.notasIndividuais.forEach {
                val nota = NotaIndividualLocal(
                    idJogatina = repo.get()!!.id,
                    responsavel = it.responsavel,
                    total = it.nota.total,
                    mecanica = it.nota.mecanica,
                    experiencia = it.nota.experiencia,
                    componentes = it.nota.componentes,
                    data = it.data
                )
                notasRepo.insert(nota)
            }
            callback()
        }
    }

    fun obterJogatinaSelecionada(callback: (JogatinaLocal?) -> Any) {
        GlobalScope.launch {
            callback(repo.get())
        }
    }

    fun obterNotasIndividuais(jogatina: JogatinaLocal, callback: (List<NotaIndividualLocal>?) -> Any) {
        callback(notasRepo.get())
    }
}