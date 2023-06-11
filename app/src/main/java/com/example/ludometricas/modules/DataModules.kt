package com.example.ludometricas.modules

import androidx.room.Room
import com.example.ludometricas.data.repos.JogatinaRepository
import com.example.ludometricas.data.repos.JogosRepository
import com.example.ludometricas.data.dao.AppDatabase
import com.example.ludometricas.data.repos.MecanicasRepository
import com.example.ludometricas.presentation.jogo.fragments.MecanicasFragment
import org.koin.dsl.bind
import org.koin.dsl.module


internal val DataModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            "ludometricas_db")
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { MecanicasFragment(get()) } bind MecanicasFragment::class
    factory { JogosRepository(get(), get()) } bind JogosRepository::class
    factory { MecanicasRepository(get()) } bind MecanicasRepository::class
    factory { JogatinaRepository(get(), get()) } bind JogatinaRepository::class
    single { get<AppDatabase>().jogosDao() }
    single { get<AppDatabase>().jogatinasDao() }
    single { get<AppDatabase>().mecanicasDao() }
    single { get<AppDatabase>().notasIndividuaisDao() }
}