package com.example.ludometricas.data

import android.util.Log
import com.example.ludometricas.data.dao.JogoLocal
import com.example.ludometricas.data.dao.JogosDao
import com.google.firebase.database.*
import com.google.gson.Gson
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.merge
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

    fun getAll(callback:(List<Jogo>) -> Any) {
        val myRef = FirebaseDatabase.getInstance(url).getReference("Jogos")
        // Lendo todos os jogos do banco
        myRef.get().addOnSuccessListener {
            val databaseJogos = it.value as Map<*, *>
            var jogos = databaseJogos.values.map { Gson().fromJson(it.toString(), Jogo::class.java) }
            inserLocalDB(jogos)
            callback(jogos)
        }.addOnFailureListener{
        }
    }

    private fun inserLocalDB(jogos: List<Jogo>) {
        GlobalScope.launch {
            //jogosDao.deleteAll()
            jogos.forEach { jogo ->
                if(jogosDao.get(jogo.nome) == null) {
                    jogosDao.insert(
                        JogoLocal(
                            jogo.nome,
                            false,
                            jogo.recorde!!.responsavel,
                            jogo.recorde!!.pontuacao,
                            dateToLong(jogo.recorde!!.data),
                            timeToLong(jogo.tempoJogado),
                            jogo.notaMediaAteOMomento.total,
                            jogo.notaMediaAteOMomento.mecanica,
                            jogo.notaMediaAteOMomento.componentes,
                            jogo.notaMediaAteOMomento.experiencia,
                            jogo.jogatinas,
                            timeToLong(jogo.tempoMedioJogatina),
                            jogo.notaTotalAteOMomento.total
                        )
                    )
                }
            }
        }
    }

    fun selecionarJogo(jogo: Jogo) {
        GlobalScope.launch {
            jogosDao.unselectAll()
            jogosDao.select(jogo.nome)
        }
    }
    fun deselecionarJogo(nomeJogo: String) {
        GlobalScope.launch {
            jogosDao.select(nomeJogo, false)
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

    fun avaliar(a: Avaliacao) {
        getOne(a.nomeJogo, fun (jogoAntigo) {
            jogoAntigo.notaMediaAteOMomento = Nota(a.notaMediaAteOMomento, (jogoAntigo.notaTotalAteOMomento.mecanica + a.notaMecanica)/(jogoAntigo.jogatinas+1),(jogoAntigo.notaTotalAteOMomento.componentes + a.notaComponentes)/(jogoAntigo.jogatinas+1),(jogoAntigo.notaTotalAteOMomento.experiencia + a.notaExperiencia)/(jogoAntigo.jogatinas+1), java.util.Date())
            jogoAntigo.notaTotalAteOMomento = Nota(jogoAntigo.notaTotalAteOMomento.total+a.notaJogatina, jogoAntigo.notaTotalAteOMomento.mecanica+a.notaMecanica, jogoAntigo.notaTotalAteOMomento.componentes+a.notaComponentes, jogoAntigo.notaTotalAteOMomento.experiencia+a.notaExperiencia,java.util.Date())
            jogoAntigo.jogatinas = jogoAntigo.jogatinas+1
            jogoAntigo.notasIndividuaisAteOMomento  = jogoAntigo.notasIndividuaisAteOMomento.plus(a.notasIndividuais)
            jogoAntigo.historicoJogatinas = jogoAntigo.historicoJogatinas.plus(Jogatina(data = java.util.Date(), notasIndividuais = a.notasIndividuais)).toMutableList()
            myRef.child(jogoAntigo.nome).setValue(Gson().toJson(jogoAntigo))
        })
    }
}