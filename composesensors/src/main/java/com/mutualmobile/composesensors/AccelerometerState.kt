package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * An acceleration sensor determines the acceleration that is applied to a device by measuring the
 * forces that are applied to the sensor itself.
 * @param xForce Acceleration force along the x axis (including gravity) in m/s^2.
 * @param yForce Acceleration force along the y axis (including gravity) in m/s^2.
 * @param zForce Acceleration force along the z axis (including gravity) in m/s^2.
 */
@Immutable
class AccelerometerState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
) : SensorState {
    override val isAvailable: Boolean
        @Composable
        get() = SensorType.Accelerometer.isSensorAvailable()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AccelerometerState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xForce.hashCode()
        result = 31 * result + yForce.hashCode()
        result = 31 * result + zForce.hashCode()
        return result
    }

    override fun toString(): String {
        return "AccelerometerState(xForce=$xForce, yForce=$yForce, zForce=$zForce)"
    }
}

/**
 * Creates and [remember]s an instance of [AccelerometerState].
 */
@Composable
fun rememberAccelerometerState(sensorDelay: SensorDelay = SensorDelay.Normal): AccelerometerState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Accelerometer,
        sensorDelay = sensorDelay
    )
    val accelerometerState = remember { mutableStateOf(AccelerometerState()) }

    LaunchedEffect(
        key1 = sensorState.value,
        block = {
            val sensorStateValues = sensorState.value
            if (sensorStateValues.isNotEmpty()) {
                accelerometerState.value = AccelerometerState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2]
                )
            }
        }
    )

    return accelerometerState.value
}
