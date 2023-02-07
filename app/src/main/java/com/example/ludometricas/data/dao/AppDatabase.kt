package com.example.ludometricas.data.dao

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(
    JogoLocal::class,
),
    version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun jogosDao(): JogosDao
}