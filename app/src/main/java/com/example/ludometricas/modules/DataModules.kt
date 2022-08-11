package com.example.ludometricas.modules

import androidx.room.Room
import com.example.ludometricas.data.JogosRepository
import com.example.ludometricas.data.dao.AppDatabase
import org.koin.dsl.bind
import org.koin.dsl.module


internal val DataModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "ludometricas_db")
            .build()
    }

    factory { JogosRepository(get()) } bind JogosRepository::class
    single { get<AppDatabase>().jogosDao() }
}