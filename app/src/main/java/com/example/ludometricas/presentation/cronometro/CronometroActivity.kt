package com.example.ludometricas.presentation.cronometro

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import com.example.ludometricas.R
import kotlinx.android.synthetic.main.activity_cronometro.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CronometroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cronometro)

        val a = StopwatchListOrchestrator(GlobalScope)
        icn_play.setOnClickListener {
            a.start()
        }

        icn_stop.setOnClickListener {
            a.stop()
            val e = StopwatchStateHolder().getStringTimeRepresentation()
        }
    }

    sealed class StopwatchState {

        data class Paused(
            val elapsedTime: Long
        ) : StopwatchState()

        data class Running(
            val startTime: Long,
            val elapsedTime: Long
        ) : StopwatchState()
    }

    class ElapsedTimeCalculator(
    ) {

        fun calculate(state: StopwatchState.Running): Long {
            val currentTimestamp = System. currentTimeMillis()
            val timePassedSinceStart = if (currentTimestamp > state.startTime) {
                currentTimestamp - state.startTime
            } else {
                0
            }
            return timePassedSinceStart + state.elapsedTime
        }
    }

    class StopwatchStateCalculator(
    ) {

        fun calculateRunningState(oldState: StopwatchState): StopwatchState.Running =
            when (oldState) {
                is StopwatchState.Running -> oldState
                is StopwatchState.Paused -> {
                    StopwatchState.Running(
                        startTime = System. currentTimeMillis(),
                        elapsedTime = oldState.elapsedTime
                    )
                }
            }

        fun calculatePausedState(oldState: StopwatchState): StopwatchState.Paused =
            when (oldState) {
                is StopwatchState.Running -> {
                    val elapsedTime = ElapsedTimeCalculator().calculate(oldState)
                    StopwatchState.Paused(elapsedTime = elapsedTime)
                }
                is StopwatchState.Paused -> oldState
            }
    }

    class TimestampMillisecondsFormatter() {

        companion object {
            const val DEFAULT_TIME = "00:00:000"
        }

        fun format(timestamp: Long): String {
            val millisecondsFormatted = (timestamp % 1000).pad(3)
            val seconds = timestamp / 1000
            val secondsFormatted = (seconds % 60).pad(2)
            val minutes = seconds / 60
            val minutesFormatted = (minutes % 60).pad(2)
            val hours = minutes / 60
            return if (hours > 0) {
                val hoursFormatted = (minutes / 60).pad(2)
                "$hoursFormatted:$minutesFormatted:$secondsFormatted"
            } else {
                "$minutesFormatted:$secondsFormatted:$millisecondsFormatted"
            }
        }

        private fun Long.pad(desiredLength: Int) = this.toString().padStart(desiredLength, '0')
    }

    class StopwatchStateHolder(
    ) {

        var currentState: StopwatchState = StopwatchState.Paused(0)
            private set

        fun start() {
            currentState = StopwatchStateCalculator().calculateRunningState(currentState)
        }

        fun pause() {
            currentState = StopwatchStateCalculator().calculatePausedState(currentState)
        }

        fun stop() {
            currentState = StopwatchState.Paused(0)
        }

        fun getStringTimeRepresentation(): String {
            val elapsedTime = when (val currentState = currentState) {
                is StopwatchState.Paused -> currentState.elapsedTime
                is StopwatchState.Running -> ElapsedTimeCalculator().calculate(currentState)
            }
            return TimestampMillisecondsFormatter().format(elapsedTime)
        }
    }

    internal class StopwatchListOrchestrator(
        val scope: CoroutineScope
    ) {
        private var job: Job? = null
        private val mutableTicker = MutableStateFlow("")
        val ticker: StateFlow<String> = mutableTicker

        fun start() {
            if (job == null) startJob()
            StopwatchStateHolder().start()
        }

        private fun startJob() {
            scope.launch {
                while (isActive) {
                    mutableTicker.value = StopwatchStateHolder().getStringTimeRepresentation()
                    delay(20)
                }
            }
        }

        fun pause() {
            StopwatchStateHolder().pause()
            stopJob()
        }

        fun stop() {
            StopwatchStateHolder().stop()
            stopJob()
            clearValue()
        }

        private fun stopJob() {
            scope.coroutineContext.cancelChildren()
            job = null
        }

        private fun clearValue() {
            mutableTicker.value = ""
        }
    }
}