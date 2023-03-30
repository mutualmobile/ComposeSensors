package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * The HeartRate sensor provided value is heart rate in beats per minute
 * @param heartRate This value is indicate current heart rate.
 * @param isAvailable Whether the current device has an accelerometer sensor. Defaults to false.
 * @param accuracy Accuracy factor of the heart rate sensor. Defaults to 0.
 */
@Immutable
class HeartRateSensorState internal constructor(
    val heartRate: Int = 0,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HeartRateSensorState) return false

        if (heartRate != other.heartRate) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = heartRate.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "HeartRateSensorState(heartRate=$heartRate isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [HeartRateSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberHeartRateSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): HeartRateSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.HeartRate,
        sensorDelay = sensorDelay,
        onError = onError,
    )
    val heartRateSensorState = remember { mutableStateOf(HeartRateSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                heartRateSensorState.value = HeartRateSensorState(
                    heartRate = sensorStateValues[0].toInt(),
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                )
            }
        },
    )

    return heartRateSensorState.value
}
