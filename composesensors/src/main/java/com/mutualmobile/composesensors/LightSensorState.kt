package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the ambient light level (illumination) in lx.
 * @param illuminance Level of brightness of the light (in lx).
 * @param isAvailable Whether the current device has a light sensor. Defaults to false.
 * @param accuracy Accuracy factor of the light sensor. Defaults to 0.
 */
@Immutable
class LightSensorState internal constructor(
    val illuminance: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LightSensorState) return false

        if (illuminance != other.illuminance) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = illuminance.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "LightSensorState(illuminance=$illuminance, isAvailable=$isAvailable, " +
                "accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [LightSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberLightSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): LightSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Light,
        sensorDelay = sensorDelay,
        onError = onError,
    )
    val lightSensorState = remember { mutableStateOf(LightSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                lightSensorState.value = LightSensorState(
                    illuminance = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return lightSensorState.value
}
