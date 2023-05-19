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
 * @param isXSupported  Acceleration supported for x-axis. Defaults to false.
 * @param isYSupported Acceleration supported for y-axis. Defaults to false.
 * @param isZSupported Acceleration supported for z-axis. Defaults to false.
 * @param isAvailable Whether the current device has an accelerometer with limited axes sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer with limited axes sensor. Defaults to 0.
 */
@Immutable
class AccelerometerLimitedAxesSensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
    val isXSupported: Boolean = false,
    val isYSupported: Boolean = false,
    val isZSupported: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccelerometerLimitedAxesSensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
        if (isXSupported != other.isXSupported) return false
        if (isYSupported != other.isYSupported) return false
        if (isZSupported != other.isZSupported) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xForce.hashCode()
        result = 31 * result + yForce.hashCode()
        result = 31 * result + zForce.hashCode()
        result = 31 * result + isXSupported.hashCode()
        result = 31 * result + isYSupported.hashCode()
        result = 31 * result + isZSupported.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy
        return result
    }
}

/**
 * Creates and [remember]s an instance of [AccelerometerLimitedAxesSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberAccelerometerLimitedAxesSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): AccelerometerLimitedAxesSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.AccelerometerLimitedAxes,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val accelerometerSensorState = remember { mutableStateOf(AccelerometerLimitedAxesSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                accelerometerSensorState.value = AccelerometerLimitedAxesSensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
                    isXSupported = sensorStateValues[3] != 0f,
                    isYSupported = sensorStateValues[4] != 0f,
                    isZSupported = sensorStateValues[5] != 0f,
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return accelerometerSensorState.value
}
