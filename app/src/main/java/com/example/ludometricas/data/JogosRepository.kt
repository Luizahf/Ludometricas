package com.example.ludometricas.data

import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.data.dao.JogosDao
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class JogosRepository(
    var jogosDao: JogosDao
) {
    private var url: String = "https://ludometricas-default-rtdb.firebaseio.com"
    val myRef = FirebaseDatabase.getInstance(url).getReference("Jogos")

    fun insert(jogos: MutableList<Jogo>) {
        // Remover todos os jogos
        //FirebaseDatabase.getInstance(url).getReference("Jogos").removeValue()

        jogos.forEach { jogo ->
            myRef.child(jogo.nome).setValue(Gson().toJson(jogo))
        }
    }
    fun insert(jogo: Jogo) {
        myRef.child(jogo.nome).setValue(Gson().toJson(jogo))
    }

    fun update(jogo: JogoLocal) {
        GlobalScope.launch {
            jogosDao.deleteOne(jogo.id)
            jogosDao.insert(jogo)
        }
    }

    fun getAll(callback:(List<Jogo>) -> Any) {
        val myRef = FirebaseDatabase.getInstance(url).getReference("Jogos")
        myRef.get().addOnSuccessListener {
            if (it.value != null) {
                val databaseJogos = it.value as Map<*, *>
                val jogos = databaseJogos.values.map { Gson().fromJson(it.toString(), Jogo::class.java) }
                inserLocalDB(jogos)
                callback(jogos)
            }
            else callback(mutableListOf())
        }.addOnFailureListener{
        }
    }

    private fun inserLocalDB(jogos: List<Jogo>) {
        GlobalScope.launch {
            jogosDao.deleteAll()
            jogos.forEach { jogo ->
                if(jogosDao.get(jogo.id) == null) {
                    jogosDao.insert(
                        JogoLocal(
                            id = jogo.id,
                            nome = jogo.nome,
                            selecionado = false,
                            RecordeResponsavel = jogo.recorde!!.responsavel,
                            RecordePontuacao = jogo.recorde!!.pontuacao,
                            RecordeData = jogo.recorde!!.data,
                            notaMediaAteOMomento = jogo.notaMediaAteOMomento.total,
                            notaMecanicaMediaAteOMomento = jogo.notaMediaAteOMomento.mecanica,
                            notaComponentesMediaAteOMomento = jogo.notaMediaAteOMomento.componentes,
                            notaExperienciaMediaAteOMomento = jogo.notaMediaAteOMomento.experiencia,
                            jogatinas = jogo.jogatinas,
                            notaTotalAteOMomento = jogo.notaTotalAteOMomento.total,
                            tempoMedioJogatina = jogo.tempoMedioJogatina.toLong()
                        )
                    )
                }
            }
        }
    }

    private fun inserOneLocalDB(jogo: Jogo) {
        GlobalScope.launch {
            if(jogosDao.get(jogo.id) == null) {
                jogosDao.insert(
                    JogoLocal(
                        id = jogo.id,
                        nome = jogo.nome,
                        selecionado = false,
                        RecordeResponsavel = jogo.recorde!!.responsavel,
                        RecordePontuacao = jogo.recorde!!.pontuacao,
                        RecordeData = jogo.recorde!!.data,
                        notaMediaAteOMomento = jogo.notaMediaAteOMomento.total,
                        notaMecanicaMediaAteOMomento = jogo.notaMediaAteOMomento.mecanica,
                        notaComponentesMediaAteOMomento = jogo.notaMediaAteOMomento.componentes,
                        notaExperienciaMediaAteOMomento = jogo.notaMediaAteOMomento.experiencia,
                        jogatinas = jogo.jogatinas,
                        notaTotalAteOMomento = jogo.notaTotalAteOMomento.total,
                        tempoMedioJogatina = jogo.tempoMedioJogatina.toLong()
                    )
                )
            }
        }
    }

    fun selecionarJogo(jogoSelecionado: Jogo, callback: () -> Any) {
        GlobalScope.launch {
            jogosDao.unselectAll()
            jogosDao.select(jogoSelecionado.id, true)
            callback()
        }
    }
    fun deselecionarJogo() {
        GlobalScope.launch {
            jogosDao.unSelect()
        }
    }

    fun obterSelecionado(callback: (JogoLocal?) -> Any) {
        GlobalScope.launch {
            callback(jogosDao.getSelected())
        }
    }

    fun getOne(nome: String, callback: (Jogo) -> Any) {
        // Lendo todos os jogos do banco
        myRef.get().addOnSuccessListener {
            val databaseJogos = it.value as Map<*, *>
            var jogos = databaseJogos.values.map { Gson().fromJson(it.toString(), Jogo::class.java) }
            callback(jogos.filter { it.nome == nome }[0])
        }.addOnFailureListener{
        }
    }

    fun avaliar(a: Avaliacao, callback: () -> Any) {

        getOne(a.nomeJogo, fun (jogoAntigo) {
            jogoAntigo.notaMediaAteOMomento = Nota(a.notaMediaAteOMomento, (jogoAntigo.notaTotalAteOMomento.mecanica + a.notaMecanica)/(jogoAntigo.jogatinas+1),(jogoAntigo.notaTotalAteOMomento.componentes + a.notaComponentes)/(jogoAntigo.jogatinas+1),(jogoAntigo.notaTotalAteOMomento.experiencia + a.notaExperiencia)/(jogoAntigo.jogatinas+1), a.notasIndividuais[0].data)
            jogoAntigo.notaTotalAteOMomento = Nota(jogoAntigo.notaTotalAteOMomento.total+a.notaJogatina, jogoAntigo.notaTotalAteOMomento.mecanica+a.notaMecanica, jogoAntigo.notaTotalAteOMomento.componentes+a.notaComponentes, jogoAntigo.notaTotalAteOMomento.experiencia+a.notaExperiencia, a.notasIndividuais[0].data)
            a.notasIndividuais.forEach { notaIndividual ->
                val notaTotal = jogoAntigo.notasTotaisIndividuais.firstOrNull { it.responsavel == notaIndividual.responsavel }
                if (notaTotal == null) {
                    jogoAntigo.notasTotaisIndividuais = jogoAntigo.notasTotaisIndividuais.plus(notaIndividual)
                } else {
                    val novaNotaTotalIndividual = Nota(notaTotal.nota.total + notaIndividual.nota.total, notaTotal.nota.mecanica + notaIndividual.nota.mecanica, notaTotal.nota.componentes+notaIndividual.nota.componentes, notaTotal.nota.experiencia+notaIndividual.nota.experiencia, notaIndividual.data)
                    jogoAntigo.notasTotaisIndividuais[jogoAntigo.notasTotaisIndividuais.indexOf(notaTotal)].nota = novaNotaTotalIndividual
                }
            }
            GlobalScope.launch {
                val jogoLocal = jogosDao.get(jogoAntigo.id)
                if (jogoLocal != null) {
                    jogoAntigo.historicoJogatinas = jogoAntigo.historicoJogatinas.plus(Jogatina(data = a.notasIndividuais[0].data, notasIndividuais = a.notasIndividuais, duracao = jogoLocal.tempoJogatina.toString(), jogoLocal.duracaoPreparacao.toString())).toMutableList()
                    jogoAntigo.tempoJogado = (jogoAntigo.tempoJogado.toLong() + jogoLocal.tempoJogatina).toString()
                    jogoAntigo.jogatinas = (jogoLocal.jogatinas + 1)
                    jogoAntigo.tempoMedioJogatina = if (jogoLocal.tempoJogatina > 0) (jogoAntigo.tempoJogado.toLong() / jogoAntigo.historicoJogatinas.filter { it.duracao.toInt() > 0 }.size).toString() else jogoAntigo.tempoMedioJogatina
                    jogoAntigo.tempoMedioPreparacao =  calcularTempoMedioPreparacao(jogoAntigo, jogoLocal)

                    if (jogoLocal.RecordeData == a.notasIndividuais[0].data) {
                        jogoAntigo.recorde = Recorde(jogoLocal.RecordeResponsavel, jogoLocal.RecordePontuacao, jogoLocal.RecordeData)
                        jogoAntigo.historicoRecordes = jogoAntigo.historicoRecordes.plus(Recorde(jogoLocal.RecordeResponsavel, jogoLocal.RecordePontuacao, jogoLocal.RecordeData)).toMutableList()
                    }
                }

                myRef.child(jogoAntigo.nome).setValue(Gson().toJson(jogoAntigo))

                atualizarBancoLocal(jogoAntigo, callback)
            }
        })
    }

    private fun calcularTempoMedioPreparacao(jogoAntigo: Jogo, jogoLocal: JogoLocal): String {
        if (jogoLocal.duracaoPreparacao > 0) {
            jogoAntigo.historicoJogatinas.forEach { jogatina ->
                var tempoTotalPreparacao : Long = 0
                var jogatinas = 1
                if (jogatina.duracaoPreparacao.toInt() > 0) {
                    tempoTotalPreparacao += jogatina.duracaoPreparacao.toLong()
                    jogatinas++
                }
                return ((tempoTotalPreparacao+jogoLocal.duracaoPreparacao)/jogatinas).toString()
            }
        }
        return jogoAntigo.tempoMedioPreparacao
    }

    private fun atualizarBancoLocal(jogo: Jogo, callback: () -> Any) {
        GlobalScope.launch {
            jogosDao.deleteOne(jogo.id)
            inserOneLocalDB(jogo)
            callback()
        }
    }

    fun getNewId(callback: (Int) -> Any) {
        GlobalScope.launch {
            val lastId = jogosDao.getIds().maxOrNull() ?: 0
            callback(lastId + 1)
        }
    }
}