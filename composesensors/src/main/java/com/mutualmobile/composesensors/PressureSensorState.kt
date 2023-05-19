package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

/**
 * Measures the ambient air pressure in hPa or mbar.
 * @param pressure Ambient air pressure in hPa or mbar.
 * @param isAvailable Whether the current device has a light sensor. Defaults to false.
 * @param accuracy Accuracy factor of the magnetic field sensor. Defaults to 0.
 */
@Immutable
class PressureSensorState internal constructor(
    val pressure: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PressureSensorState) return false

        if (pressure != other.pressure) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = pressure.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "PressureSensorState(pressure=$pressure, isAvailable=$isAvailable, accuracy=$accuracy)"
    }
}

/**
 * Creates and [remember]s an instance of [PressureSensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberPressureSensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {}
): PressureSensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.Pressure,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val pressureSensorState = remember { mutableStateOf(PressureSensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                pressureSensorState.value = PressureSensorState(
                    pressure = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return pressureSensorState.value
}
