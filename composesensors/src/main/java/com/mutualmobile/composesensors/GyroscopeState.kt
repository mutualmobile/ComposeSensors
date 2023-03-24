package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures a device's rate of rotation in rad/s around each of the three physical axes
 * (x, y, and z).
 * @param xRotation Rate of rotation around the x axis.
 * @param yRotation Rate of rotation around the y axis.
 * @param zRotation Rate of rotation around the z axis.
 */
@Immutable
class GyroscopeState internal constructor(
    val xRotation: Float = 0f,
    val yRotation: Float = 0f,
    val zRotation: Float = 0f,
) : SensorState {
    override val isAvailable: Boolean
        @Composable
        get() = SensorType.Gyroscope.isSensorAvailable()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GyroscopeState) return false

        if (xRotation != other.xRotation) return false
        if (yRotation != other.yRotation) return false
        if (zRotation != other.zRotation) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xRotation.hashCode()
        result = 31 * result + yRotation.hashCode()
        result = 31 * result + zRotation.hashCode()
        return result
    }

    override fun toString(): String {
        return "GyroscopeState(xRotation=$xRotation, yRotation=$yRotation, " +
                "zRotation=$zRotation)"
    }
}

/**
 * Creates and [remember]s an instance of [GyroscopeState].
 */
@Composable
fun rememberGyroscopeState(sensorDelay: SensorDelay = SensorDelay.Normal): GyroscopeState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Gyroscope,
        sensorDelay = sensorDelay
    )
    val gyroscopeState = remember { mutableStateOf(GyroscopeState()) }

    LaunchedEffect(
        key1 = sensorState.value,
        block = {
            val sensorStateValues = sensorState.value
            if (sensorStateValues.isNotEmpty()) {
                gyroscopeState.value = GyroscopeState(
                    xRotation = sensorStateValues[0],
                    yRotation = sensorStateValues[1],
                    zRotation = sensorStateValues[2]
                )
            }
        }
    )

    return gyroscopeState.value
}
