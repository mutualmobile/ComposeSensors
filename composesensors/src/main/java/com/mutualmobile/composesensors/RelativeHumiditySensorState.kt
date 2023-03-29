package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlin.math.exp
import kotlin.math.ln

/**
 * Measures the ambient light level (illumination) in lx.
 * @param relativeHumidity Level of relative humidity in the air (in percentage).
 * @param isAvailable Whether the current device has a relative humidity sensor. Defaults to false.
 * @param accuracy Accuracy factor of the relative humidity sensor. Defaults to 0.
 */
@Immutable
class HumiditySensorState internal constructor(
    val relativeHumidity: Float = 0f,
    val isAvailable: Boolean = false,
    val accuracy: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is HumiditySensorState) return false

        if (relativeHumidity != other.relativeHumidity) return false
        if (isAvailable != other.isAvailable) return false
        if (accuracy != other.accuracy) return false

        return true
    }

    override fun hashCode(): Int {
        var result = relativeHumidity.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        return result
    }

    override fun toString(): String {
        return "HumiditySensorState(relativeHumidity=$relativeHumidity, isAvailable=$isAvailable, " +
                "accuracy=$accuracy)"
    }


    fun getDewPointTemp(relativeHumidity: Float, actualTemp: Float): Double {
        val h = ln(relativeHumidity / 100.0) + (17.62 * actualTemp) / (243.12 + actualTemp)
        return 243.12 * (h / (17.62 - h))
    }

    fun getAbsHumidity(relativeHumidity: Float, actualTemp: Float): Double = 216.7 *
            (relativeHumidity / 100.0 * 6.112 * exp(17.62 * actualTemp / (243.12 + actualTemp)) / (273.15 + actualTemp));
}

/**
 * Creates and [remember]s an instance of [HumiditySensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberHumiditySensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    onError: (throwable: Throwable) -> Unit = {},
): HumiditySensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.RelativeHumidity,
        sensorDelay = sensorDelay,
        onError = onError,
    )
    val humiditySensorState = remember { mutableStateOf(HumiditySensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                humiditySensorState.value = HumiditySensorState(
                    relativeHumidity = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy
                )
            }
        }
    )

    return humiditySensorState.value
}