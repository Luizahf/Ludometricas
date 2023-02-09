package com.example.ludometricas.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.ludometricas.data.dataClasses.JogatinaLocal
import com.example.ludometricas.data.dataClasses.NotaIndividualLocal

@Database(entities = arrayOf(
    JogoLocal::class,
    JogatinaLocal::class,
    NotaIndividualLocal::class
),
    version = 8, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jogosDao(): JogosDao
    abstract fun jogatinasDao(): JogatinasDao
    abstract fun notasIndividuaisDao(): NotasIndividuaisDao
}