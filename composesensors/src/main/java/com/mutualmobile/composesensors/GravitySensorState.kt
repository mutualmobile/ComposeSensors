package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the force of gravity in m/s2 that is applied to a device on all three physical axes
 * (x, y, z).
 * @param xForce Force of gravity along the x axis. Defaults to 0f.
 * @param yForce Force of gravity along the y axis. Defaults to 0f.
 * @param zForce Force of gravity along the z axis. Defaults to 0f.
 * @param isAvailable Whether the current device has a Gravity sensor. Defaults to false.
 * @param accuracy Accuracy factor of the Gravity sensor. Defaults to 0.
 */
@Immutable
class GravitySensorState internal constructor(
    val xForce: Float = 0f,
    val yForce: Float = 0f,
    val zForce: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GravitySensorState) return false

        if (xForce != other.xForce) return false
        if (yForce != other.yForce) return false
        if (zForce != other.zForce) return false
        if (isAvailable != other.isAvailable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xForce.hashCode()
        result = 31 * result + yForce.hashCode()
        result = 31 * result + zForce.hashCode()
        result = 31 * result + isAvailable.hashCode()
        return result
    }

    override fun toString(): String {
        return "GravitySensorState(xForce=$xForce, yForce=$yForce, " +
                "zForce=$zForce, isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [GravitySensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberGravitySensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): GravitySensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Gravity,
        sensorDelay = sensorDelay,
        onError = onError,
    )
    val gravitySensorState = remember { mutableStateOf(GravitySensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                gravitySensorState.value = GravitySensorState(
                    xForce = sensorStateValues[0],
                    yForce = sensorStateValues[1],
                    zForce = sensorStateValues[2],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return gravitySensorState.value
}
