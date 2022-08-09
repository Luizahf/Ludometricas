package com.example.ludometricas.modules

import androidx.room.Room
import com.example.ludometricas.data.JogosRepository
import org.koin.dsl.bind
import org.koin.dsl.module


internal val DataModules = module {
    factory { JogosRepository() } bind JogosRepository::class
}