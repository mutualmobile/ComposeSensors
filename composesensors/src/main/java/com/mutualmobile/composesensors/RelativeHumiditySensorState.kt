package com.mutualmobile.composesensors

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlin.math.exp
import kotlin.math.ln

/**
 * Measures the Relative humidity value.
 *
 * Dew Point is dew point is the temperature to which a given parcel of air must be cooled.
 * Absolute humidity is the mass of water vapor in a particular volume of dry air. The unit is g/m3
 * [Relative Humidity](https://developer.android.com/reference/android/hardware/SensorEvent#sensor.type_relative_humidity:)
 *
 * @param relativeHumidity Level of relative humidity in the air (in percentage).
 * @param isAvailable Whether the current device has a relative humidity sensor. Defaults to false.
 * @param actualTemp Actual current temperature as measured by the device. Defaults to 0.
 * @param accuracy Accuracy factor of the relative humidity sensor. Defaults to 0.
 */
@Immutable
class RelativeHumiditySensorState internal constructor(
    val relativeHumidity: Float = 0f,
    val isAvailable: Boolean = false,
    val actualTemp: Float = 0f,
    val accuracy: Int = 0
) {
    val absoluteHumidity = 216.7 *
        (
            relativeHumidity / 100.0 * 6.112 * exp(
                17.62 * actualTemp /
                    (243.12 + actualTemp)
            ) / (273.15 + actualTemp)
            )

    private val humidity = ln(relativeHumidity / 100.0) +
        (17.62 * actualTemp) / (243.12 + actualTemp)

    val dewPointTemperature = 243.12 * (humidity / (17.62 - humidity))

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RelativeHumiditySensorState) return false

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
        return "HumiditySensorState(relativeHumidity=$relativeHumidity, " +
            "isAvailable=$isAvailable, " +
            "accuracy=$accuracy)"
    }

    private fun getDewPointTemp(relativeHumidity: Float, actualTemp: Float): Double {
        val h = ln(relativeHumidity / 100.0) + (17.62 * actualTemp) / (243.12 + actualTemp)
        return 243.12 * (h / (17.62 - h))
    }
}

/**
 * Creates and [remember]s an instance of [RelativeHumiditySensorState].
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param actualTemp is passed into the function only if User requires calculations on Dew Point and absolute Humidity
 * This can be accessed using the dot operator. (e.g ->  rememberRelativeHumiditySensorState().absoluteHumidity)
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberRelativeHumiditySensorState(
    sensorDelay: SensorDelay = SensorDelay.Normal,
    actualTemp: Float = 0F,
    onError: (throwable: Throwable) -> Unit = {}
): RelativeHumiditySensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.RelativeHumidity,
        sensorDelay = sensorDelay,
        onError = onError
    )
    val relativeHumiditySensorState = remember { mutableStateOf(RelativeHumiditySensorState()) }

    LaunchedEffect(
        key1 = sensorState,
        block = {
            val sensorStateValues = sensorState.data
            if (sensorStateValues.isNotEmpty()) {
                relativeHumiditySensorState.value = RelativeHumiditySensorState(
                    relativeHumidity = sensorStateValues[0],
                    isAvailable = sensorState.isAvailable,
                    accuracy = sensorState.accuracy,
                    actualTemp = actualTemp
                )
            }
        }
    )

    return relativeHumiditySensorState.value
}
