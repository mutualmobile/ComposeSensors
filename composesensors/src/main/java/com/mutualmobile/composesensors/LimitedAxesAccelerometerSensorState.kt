package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An acceleration sensor determines the acceleration that is applied to a device by measuring the
 * forces that are applied to the sensor itself.
 * @param xForce Acceleration minus Gx on the x-axis (if supported)
 * @param yForce  Acceleration minus Gy on the y-axis (if supported)
 * @param zForce Acceleration minus Gz on the z-axis (if supported)
 * @param xAxisSupported  Acceleration supported for x-axis. Defaults to false.
 * @param yAxisSupported Acceleration supported for y-axis. Defaults to false.
 * @param zAxisSupported Acceleration supported for z-axis. Defaults to false.
 * @param isAvailable Whether the current device has an accelerometer with limited axes sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer with limited axes sensor. Defaults to 0.
 */
@Immutable
class LimitedAxesAccelerometerSensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
    val xAxisSupported: Boolean = false,
    val yAxisSupported: Boolean = false,
    val zAxisSupported: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LimitedAxesAccelerometerSensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
        if (xAxisSupported != other.xAxisSupported) return false
        if (yAxisSupported != other.yAxisSupported) return false
        if (zAxisSupported != other.zAxisSupported) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xForce.hashCode()
        result = 31 * result + yForce.hashCode()
        result = 31 * result + zForce.hashCode()
        result = 31 * result + xAxisSupported.hashCode()
        result = 31 * result + yAxisSupported.hashCode()
        result = 31 * result + zAxisSupported.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy
        return result
    }

    override fun toString(): String {
        return "LimitedAxesAccelerometerSensorState(xForce=$xForce, yForce=$yForce, " +
            "zForce=$zForce, xAxisSupported=$xAxisSupported, yAxisSupported=$yAxisSupported, " +
            "zAxisSupported=$zAxisSupported, isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [LimitedAxesAccelerometerSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberLimitedAxesAccelerometerSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): LimitedAxesAccelerometerSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.AccelerometerLimitedAxes,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val accelerometerSensorState = remember { mutableStateOf(LimitedAxesAccelerometerSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                accelerometerSensorState.value = LimitedAxesAccelerometerSensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
                    xAxisSupported = sensorStateValues[3] != 0f,
                    yAxisSupported = sensorStateValues[4] != 0f,
                    zAxisSupported = sensorStateValues[5] != 0f,
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return accelerometerSensorState.value
}
