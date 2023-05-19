package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An acceleration sensor determines the acceleration that is applied to a device by measuring the
 * forces that are applied to the sensor itself.
 * @param xForce Uncalibrated X force without bias compensation (if supported).
 * @param yForce Uncalibrated Y force without bias compensation (if supported).
 * @param zForce Uncalibrated Z force without bias compensation (if supported).
 * @param xBias Estimated X bias (if supported).
 * @param yBias Estimated Y bias (if supported).
 * @param zBias Estimated Z bias (if supported).
 * @param xAxisSupported  Acceleration supported for x-axis. Defaults to false.
 * @param yAxisSupported Acceleration supported for y-axis. Defaults to false.
 * @param zAxisSupported Acceleration supported for z-axis. Defaults to false.
 * @param isAvailable Whether the current device has an accelerometer limited axes (uncalibrated) sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer limited axes (uncalibrated) sensor. Defaults to 0.
 */
@Immutable
class AccelerometerLimitedAxesUncalibratedSensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
    val xBias: Float = 0f,
    val yBias: Float = 0f,
    val zBias: Float = 0f,
    val xAxisSupported: Boolean = false,
    val yAxisSupported: Boolean = false,
    val zAxisSupported: Boolean = false,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccelerometerLimitedAxesUncalibratedSensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
        if (xBias != other.xBias) return false
        if (yBias != other.yBias) return false
        if (zBias != other.zBias) return false
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
        result = 31 * result + xBias.hashCode()
        result = 31 * result + yBias.hashCode()
        result = 31 * result + zBias.hashCode()
        result = 31 * result + xAxisSupported.hashCode()
        result = 31 * result + yAxisSupported.hashCode()
        result = 31 * result + zAxisSupported.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy
        return result
    }

    override fun toString(): String {
        return "AccelerometerLimitedAxesUncalibratedSensorState(xForce=$xForce, yForce=$yForce, " +
            "zForce=$zForce, xBias=$xBias, yBias=$yBias, zBias=$zBias, " +
            "xAxisSupported=$xAxisSupported, yAxisSupported=$yAxisSupported, " +
            "zAxisSupported=$zAxisSupported, isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [AccelerometerLimitedAxesUncalibratedSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberAccelerometerLimitedAxesUncalibratedSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): AccelerometerLimitedAxesUncalibratedSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.AccelerometerLimitedAxesUncalibrated,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val accelerometerSensorState = remember { mutableStateOf(AccelerometerLimitedAxesUncalibratedSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                accelerometerSensorState.value = AccelerometerLimitedAxesUncalibratedSensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
                    xBias = sensorStateValues[3],
                    yBias = sensorStateValues[4],
                    zBias = sensorStateValues[5],
                    xAxisSupported = sensorStateValues[6] != 0f,
                    yAxisSupported = sensorStateValues[7] != 0f,
                    zAxisSupported = sensorStateValues[8] != 0f,
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return accelerometerSensorState.value
}
