package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Equivalent to Gyroscope Sensor but supporting cases where one or two axes are not supported.
 * @param xRotation Angular speed around the x-axis (if supported). Defaults to 0f.
 * @param yRotation Angular speed around the y-axis (if supported). Defaults to 0f.
 * @param zRotation Angular speed around the z-axis (if supported). Defaults to 0f.
 * @param xAxisSupported Angular speed supported for x-axis. Defaults to 0f.
 * @param yAxisSupported Angular speed supported for y-axis. Defaults to 0f.
 * @param zAxisSupported Angular speed supported for z-axis. Defaults to 0f.
 * @param isAvailable Whether the current device has a gyroscope sensor. Defaults to false.
 * @param accuracy Accuracy factor of the gyroscope sensor. Defaults to 0.
 */
@Immutable
class LimitedAxesGyroscopeSensorState internal constructor(
    val xRotation: Float = 0f,
    val yRotation: Float = 0f,
    val zRotation: Float = 0f,
    val xAxisSupported: Float = 0f,
    val yAxisSupported: Float = 0f,
    val zAxisSupported: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LimitedAxesGyroscopeSensorState

        if (xRotation != other.xRotation) return false
        if (yRotation != other.yRotation) return false
        if (zRotation != other.zRotation) return false
        if (xAxisSupported != other.xAxisSupported) return false
        if (yAxisSupported != other.yAxisSupported) return false
        if (zAxisSupported != other.zAxisSupported) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xRotation.hashCode()
        result = 31 * result + yRotation.hashCode()
        result = 31 * result + zRotation.hashCode()
        result = 31 * result + xAxisSupported.hashCode()
        result = 31 * result + yAxisSupported.hashCode()
        result = 31 * result + zAxisSupported.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy
        return result
    }

    override fun toString(): String {
        return "LimitedAxesGyroscopeSensorState(xRotation=$xRotation, yRotation=$yRotation, zRotation=$zRotation, xAxisSupported=$xAxisSupported, yAxisSupported=$yAxisSupported, zAxisSupported=$zAxisSupported, isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [LimitedAxesGyroscopeSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */

@Composable
fun rememberLimitedAxesGyroscopeSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): LimitedAxesGyroscopeSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.GyroscopeLimitedAxes,
        sensorDelay = sensorDelay,
        onError = onError
    )

    val gyroscopeLimitedAxesSensor = remember { mutableStateOf(LimitedAxesGyroscopeSensorState()) }

    LaunchedEffect(key1 = sensorState, block = {
        val sensorStateValues = sensorState.data
        if (sensorStateValues.isNotEmpty()) {
            gyroscopeLimitedAxesSensor.value = LimitedAxesGyroscopeSensorState(
                xRotation = sensorStateValues[0],
                yRotation = sensorStateValues[1],
                zRotation = sensorStateValues[2],
                xAxisSupported = sensorStateValues[3],
                yAxisSupported = sensorStateValues[4],
                zAxisSupported = sensorStateValues[5],
                isAvailable = sensorState.isAvailable,
                accuracy = sensorState.accuracy
            )
        }
    })

    return gyroscopeLimitedAxesSensor.value
}
