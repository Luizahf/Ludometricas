package com.example.ludometricas.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface JogosDao {
    @Query("SELECT * FROM jogos")
    fun get(): List<JogoLocal>?

    @Query("SELECT * FROM jogos WHERE id = :id")
    fun get(id: Int): JogoLocal?

    @Query("SELECT id FROM jogos")
    fun getIds(): List<Int>

    @Query("SELECT * FROM jogos WHERE selecionado = :select")
    fun getSelected(select: Boolean = true): JogoLocal?

    @Query("UPDATE jogos SET selecionado = :select WHERE id = :id")
    fun select(id: Int, select: Boolean = true)

    @Query("UPDATE jogos SET selecionado = :selecionado")
    fun unSelect(selecionado: Boolean? = false)

    @Query("UPDATE jogos SET nome = :novoNome WHERE id = :id")
    fun rename(id: Int, novoNome: String)

    @Query("UPDATE jogos SET selecionado = :select")
    fun unselectAll(select: Boolean = false)

    @Insert
    fun insert(jogos: JogoLocal)

    @Query("DELETE FROM jogos")
    fun deleteAll()

    @Query("DELETE FROM jogos WHERE id = :id")
    fun deleteOne(id: Int)
}