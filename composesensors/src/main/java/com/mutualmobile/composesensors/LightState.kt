package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the ambient light level (illumination) in lx.
 * @param illuminance Level of brightness of the light.
 */
@Immutable
class LightState internal constructor(
    val illuminance: Float = 0f,
) : SensorState {
    override val isAvailable: Boolean
        @Composable
        get() = SensorType.Light.isSensorAvailable()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LightState) return false

        if (illuminance != other.illuminance) return false

        return true
    }

    override fun hashCode(): Int {
        return illuminance.hashCode()
    }

    override fun toString(): String {
        return "LightState(illuminance=$illuminance)"
    }
}

/**
 * Creates and [remember]s an instance of [LightState].
 */
@Composable
fun rememberLightState(sensorDelay: SensorDelay = SensorDelay.Normal): LightState {
    val sensorState = rememberSensorState(sensorType = SensorType.Light, sensorDelay = sensorDelay)
    val lightState = remember { mutableStateOf(LightState()) }

    LaunchedEffect(
        key1 = sensorState.value,
        block = {
            val sensorStateValues = sensorState.value
            if (sensorStateValues.isNotEmpty()) {
                lightState.value = LightState(illuminance = sensorStateValues[0])
            }
        }
    )

    return lightState.value
}
