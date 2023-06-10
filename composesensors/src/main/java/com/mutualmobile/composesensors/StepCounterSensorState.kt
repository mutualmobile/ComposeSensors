package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * The step counter sensor provides the number of steps taken by the user since the last reboot while the sensor was activated.
 * The step counter has more latency (up to 10 seconds) but more accuracy than the step detector sensor.
 * @param stepCount Number of steps taken by the user since the last reboot while the sensor was activated.
 * @param isAvailable Whether the current device has an accelerometer sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer sensor. Defaults to 0.
 */
@Immutable
class StepCounterSensorState internal constructor(
    val stepCount: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StepCounterSensorState) return false

        if (stepCount != other.stepCount) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stepCount.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "StepCounterSensorState(stepCount=$stepCount isAvailable=$isAvailable," +
            " accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [StepCounterSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberStepCounterSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): StepCounterSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.StepCounter,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val stepCounterSensorState = remember { mutableStateOf(StepCounterSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                stepCounterSensorState.value = StepCounterSensorState(
                    stepCount = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return stepCounterSensorState.value
}
