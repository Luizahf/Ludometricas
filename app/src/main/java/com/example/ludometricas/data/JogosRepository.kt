package com.example.ludometricas.data

import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.data.dao.JogosDao
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Date
import java.sql.Time

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

//        myRef.child("Teste").setValue(Gson().toJson(
//            Jogo(
//                0, "Teste", notaMediaAteOMomento = Nota(0.0, 0.0, 0.0, 0.0),
//                notaTotalAteOMomento = Nota(0.0, 0.0, 0.0, 0.0),
//                notasIndividuaisAteOMomento = mutableListOf(),
//                notasTotaisIndividuais = mutableListOf(), jogatinas = 0, tempoJogado = "0", tempoMedioJogatina = "0")
//        ))

        // Lendo todos os jogos do banco
        myRef.get().addOnSuccessListener {
            val databaseJogos = it.value as Map<*, *>
            val jogos = databaseJogos.values.map { Gson().fromJson(it.toString(), Jogo::class.java) }
            inserLocalDB(jogos)
            callback(jogos)
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
                            jogo.id,
                            jogo.nome,
                            false,
                            jogo.recorde!!.responsavel,
                            jogo.recorde!!.pontuacao,
                            jogo.recorde!!.data,
                            jogo.tempoJogado.toLong(),
                            jogo.notaMediaAteOMomento.total,
                            jogo.notaMediaAteOMomento.mecanica,
                            jogo.notaMediaAteOMomento.componentes,
                            jogo.notaMediaAteOMomento.experiencia,
                            jogo.jogatinas,
                            jogo.notaTotalAteOMomento.total,
                            jogo.tempoMedioJogatina.toLong()
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
                        jogo.id,
                        jogo.nome,
                        false,
                        jogo.recorde!!.responsavel,
                        jogo.recorde!!.pontuacao,
                        jogo.recorde!!.data,
                        jogo.tempoJogado.toLong(),
                        jogo.notaMediaAteOMomento.total,
                        jogo.notaMediaAteOMomento.mecanica,
                        jogo.notaMediaAteOMomento.componentes,
                        jogo.notaMediaAteOMomento.experiencia,
                        jogo.jogatinas,
                        jogo.notaTotalAteOMomento.total,
                        jogo.tempoMedioJogatina.toLong()
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

    fun longToDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }
    fun longToTime(dateLong: Long?): Time? {
        return dateLong?.let { Time(it) }
    }

    fun dateToLong(date: Date?): Long? {
        return if (date == null) null else date.getTime()
    }
    fun timeToLong(date: Time?): Long? {
        return if (date == null) null else date.getTime()
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
            jogoAntigo.notaMediaAteOMomento = Nota(a.notaMediaAteOMomento, (jogoAntigo.notaTotalAteOMomento.mecanica + a.notaMecanica)/(jogoAntigo.jogatinas+1),(jogoAntigo.notaTotalAteOMomento.componentes + a.notaComponentes)/(jogoAntigo.jogatinas+1),(jogoAntigo.notaTotalAteOMomento.experiencia + a.notaExperiencia)/(jogoAntigo.jogatinas+1), java.util.Date().toString())
            jogoAntigo.notaTotalAteOMomento = Nota(jogoAntigo.notaTotalAteOMomento.total+a.notaJogatina, jogoAntigo.notaTotalAteOMomento.mecanica+a.notaMecanica, jogoAntigo.notaTotalAteOMomento.componentes+a.notaComponentes, jogoAntigo.notaTotalAteOMomento.experiencia+a.notaExperiencia,java.util.Date().toString())
            jogoAntigo.notasIndividuaisAteOMomento  = jogoAntigo.notasIndividuaisAteOMomento.plus(a.notasIndividuais)
            GlobalScope.launch {
                val jogoLocal = jogosDao.get(jogoAntigo.id)
                if (jogoLocal != null) {
                    jogoAntigo.historicoJogatinas = jogoAntigo.historicoJogatinas.plus(Jogatina(data = java.util.Date().toString(), notasIndividuais = a.notasIndividuais, duracao = jogoLocal.tempoJogatina.toString())).toMutableList()
                    jogoAntigo.tempoJogado = (jogoAntigo.tempoJogado.toLong() + jogoLocal.tempoJogatina).toString()
                    jogoAntigo.jogatinas = jogoLocal.jogatinas
                    jogoAntigo.tempoMedioJogatina = if (jogoLocal.tempoJogatina > 0) (jogoAntigo.tempoJogado.toLong() / jogoAntigo.jogatinas).toString() else jogoAntigo.tempoMedioJogatina
                    jogoAntigo.recorde = Recorde(jogoLocal.RecordeResponsavel, jogoLocal.RecordePontuacao, jogoLocal.RecordeData)
                    jogoAntigo.historicoRecordes = jogoAntigo.historicoRecordes.plus(Recorde(jogoLocal.RecordeResponsavel, jogoLocal.RecordePontuacao, jogoLocal.RecordeData)).toMutableList()

                }

                myRef.child(jogoAntigo.nome).setValue(Gson().toJson(jogoAntigo))

                atualizarBancoLocal(jogoAntigo, callback)
            }
        })
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
            var oi = jogosDao.getIds()
            var oie = oi.maxOrNull()
            callback(jogosDao.getIds().maxOrNull()!!+1)
        }
    }
}