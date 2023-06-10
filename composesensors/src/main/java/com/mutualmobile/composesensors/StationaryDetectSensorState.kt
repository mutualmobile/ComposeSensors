package com.mutualmobile.composesensors

import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * The Stationary Detect sensor ([Sensor.TYPE_STATIONARY_DETECT]) is used to detect whether the
 * device is stationary or in motion. This sensor provides a binary output, with a value of either
 * false or true, indicating the device's motion state.
 *
 * For more info, please refer the [Android Documentation Reference](https://developer.android.com/reference/android/hardware/SensorEvent#sensor.type_stationary_detect:)
 *
 * @param isDeviceStationary Whether the device is stationary or not. Defaults to false.
 * @param isAvailable Whether the current device has a heart beat sensor. Defaults to false.
 * @param accuracy Accuracy factor of the heart beat sensor. Defaults to 0.
 */
@RequiresApi(Build.VERSION_CODES.N)
@Immutable
class StationaryDetectSensorState internal constructor(
    val isDeviceStationary: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StationaryDetectSensorState) return false

        if (isDeviceStationary != other.isDeviceStationary) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = isDeviceStationary.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "StationaryDetectSensorState(isDeviceStationary=$isDeviceStationary," +
            " isAvailable=$isAvailable, accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [StationaryDetectSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun rememberStationaryDetectSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): StationaryDetectSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.StationaryDetect,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val confidenceSensorState = remember { mutableStateOf(StationaryDetectSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                confidenceSensorState.value = StationaryDetectSensorState(
                    isDeviceStationary = sensorStateValues[0] == 1f,
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return confidenceSensorState.value
}
