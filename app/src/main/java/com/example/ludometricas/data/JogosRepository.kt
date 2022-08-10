package com.example.ludometricas.data

import com.google.firebase.database.*
import com.google.gson.Gson

class JogosRepository(
) {
    private var url: String = "https://ludometricas-default-rtdb.firebaseio.com"

    fun insert(jogos: MutableList<Jogo>) {
        // Remover todos os jogos
        FirebaseDatabase.getInstance(url).getReference("Jogos").removeValue()
        val myRef = FirebaseDatabase.getInstance(url).getReference("Jogos")
        jogos.forEach { jogo ->
            myRef.child(jogo.nome).setValue(Gson().toJson(jogo))
        }
    }

    fun getAll(callback:(List<Jogo>) -> Any) {//}: List<Jogo>? {
        val myRef = FirebaseDatabase.getInstance(url).getReference("Jogos")
        // Lendo todos os jogos do banco
        myRef.get().addOnSuccessListener { it ->
            val databaseJogos = it.value as Map<*, *>
            callback(databaseJogos.values.map { Gson().fromJson(it.toString(), Jogo::class.java) })
        }.addOnFailureListener{
        }
    }

    fun get(nomeJogo: String) { //: Jogo? {

    }
}