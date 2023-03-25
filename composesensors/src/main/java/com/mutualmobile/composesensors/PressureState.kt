package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the ambient air pressure in hPa or mbar.
 * @param pressure Ambient air pressure.
 */
@Immutable
class PressureState internal constructor(
    val pressure: Float = 0f,
) : SensorState {
    override val isAvailable: Boolean
        @Composable
        get() = SensorType.Pressure.isSensorAvailable()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PressureState) return false

        if (pressure != other.pressure) return false

        return true
    }

    override fun hashCode(): Int {
        return pressure.hashCode()
    }

    override fun toString(): String {
        return "PressureState(pressure=$pressure)"
    }
}

/**
 * Creates and [remember]s an instance of [PressureState].
 */
@Composable
fun rememberPressureState(sensorDelay: SensorDelay = SensorDelay.Normal): PressureState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Pressure,
        sensorDelay = sensorDelay
    )
    val pressureState = remember { mutableStateOf(PressureState()) }

    LaunchedEffect(
        key1 = sensorState.value,
        block = {
            val sensorStateValues = sensorState.value
            if (sensorStateValues.isNotEmpty()) {
                pressureState.value = PressureState(
                    pressure = sensorStateValues[0]
                )
            }
        }
    )

    return pressureState.value
}
