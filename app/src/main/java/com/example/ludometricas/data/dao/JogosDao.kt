package com.example.ludometricas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface JogosDao {
    @Query("SELECT * FROM jogos")
    fun get(): List<JogoLocal>?

    @Query("SELECT * FROM jogos WHERE nome = :nomeJogo")
    fun get(nomeJogo: String): JogoLocal?

    @Query("SELECT * FROM jogos WHERE selecionado = :select")
    fun getSelected(select: Boolean = true): JogoLocal?

    @Query("UPDATE jogos SET selecionado = :select WHERE nome = :nomeJogo")
    fun select(nomeJogo: String, select: Boolean = true)

    @Query("UPDATE jogos SET selecionado = :select")
    fun unselectAll(select: Boolean = false)

    @Insert
    fun insert(jogos: JogoLocal)

    @Query("DELETE FROM jogos")
    fun deleteAll()
}