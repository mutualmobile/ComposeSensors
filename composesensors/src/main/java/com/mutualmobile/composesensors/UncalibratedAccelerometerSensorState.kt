package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An acceleration sensor determines the acceleration that is applied to a device by measuring the
 * forces that are applied to the sensor itself.
 * In uncalibrated sensor, there are extra biases as estimated in x, y, z direction, such that
 * uncalibrated =_value = calibrated_value + bias in that particular direction
 *
 * @param xForce Acceleration force along the x axis (including gravity) in m/s^2. Defaults to 0f.
 * @param yForce Acceleration force along the y axis (including gravity) in m/s^2. Defaults to 0f.
 * @param zForce Acceleration force along the z axis (including gravity) in m/s^2. Defaults to 0f.
 * @param isAvailable Whether the current device has an accelerometer sensor. Defaults to false.
 * @param accuracy Accuracy factor of the accelerometer sensor. Defaults to 0.
 */
@Immutable
class UncalibratedAccelerometerSensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
    val xBiased: Float = 0f,
    val yBiased: Float = 0f,
    val zBiased: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is UncalibratedAccelerometerSensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
        if (xBiased != other.xBiased) return false
        if (yBiased != other.yBiased) return false
        if (zBiased != other.zBiased) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xForce.hashCode()
        result = 31 * result + yForce.hashCode()
        result = 31 * result + zForce.hashCode()
        result = 31 * result + xBiased.hashCode()
        result = 31 * result + yBiased.hashCode()
        result = 31 * result + zBiased.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "AccelerometerSensorState(xForce=$xForce, yForce=$yForce, zForce=$zForce, " +
                "isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [UncalibratedAccelerometerSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberUncalibratedAccelerometerSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): UncalibratedAccelerometerSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Accelerometer,
        sensorDelay = sensorDelay,
        onError = onError,
    )
    val uncalibratedAccelerometerSensorState =
        remember { mutableStateOf(UncalibratedAccelerometerSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                uncalibratedAccelerometerSensorState.value = UncalibratedAccelerometerSensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
                    xBiased = sensorStateValues[3],
                    yBiased = sensorStateValues[4],
                    zBiased = sensorStateValues[5],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return uncalibratedAccelerometerSensorState.value
}
