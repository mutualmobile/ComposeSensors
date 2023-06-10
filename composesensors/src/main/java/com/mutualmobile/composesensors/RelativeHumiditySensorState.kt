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
 * Dew Point is the temperature to which a given parcel of air must be cooled. The unit is Centigrade.
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
    val accuracy: Int = 0,
    private val startListeningEvents: (() -> Unit)? = null,
    private val stopListeningEvents: (() -> Unit)? = null
) : SensorStateListener {
    val absoluteHumidity = 216.7
        .times(relativeHumidity)
        .div(100.0)
        .times(6.112)
        .times(
            exp(
                17.62
                    .times(actualTemp)
                    .div(243.12.plus(actualTemp))
            ).div(273.15.plus(actualTemp))
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
        if (startListeningEvents != other.startListeningEvents) return false
        if (stopListeningEvents != other.stopListeningEvents) return false

        return true
    }

    override fun hashCode(): Int {
        var result = relativeHumidity.hashCode()
        result = 31 * result + isAvailable.hashCode()
        result = 31 * result + accuracy.hashCode()
        result = 31 * result + startListeningEvents.hashCode()
        result = 31 * result + stopListeningEvents.hashCode()
        return result
    }

    override fun toString(): String {
        return "HumiditySensorState(relativeHumidity=$relativeHumidity, " +
            "isAvailable=$isAvailable, " +
            "accuracy=$accuracy)"
    }

    override fun startListening() {
        startListeningEvents?.invoke()
    }

    override fun stopListening() {
        stopListeningEvents?.invoke()
    }
}

/**
 * Creates and [remember]s an instance of [RelativeHumiditySensorState].
 * @param autoStart Start listening to sensor events as soon as sensor state is initialised.
 * Defaults to true.
 * @param sensorDelay The rate at which the raw sensor data should be received.
 * Defaults to [SensorDelay.Normal].
 * @param actualTemp is passed into the function only if User requires calculations on Dew Point
 * and absolute Humidity. This can be accessed using the dot operator. (e.g ->
 * rememberRelativeHumiditySensorState().absoluteHumidity)
 * @param onError Callback invoked on every error state.
 */
@Composable
fun rememberRelativeHumiditySensorState(
    autoStart: Boolean = true,
    sensorDelay: SensorDelay = SensorDelay.Normal,
    actualTemp: Float = 0f,
    onError: (throwable: Throwable) -> Unit = {}
): RelativeHumiditySensorState {
    val sensorState = rememberSensorState(
        sensorType = SensorType.RelativeHumidity,
        sensorDelay = sensorDelay,
        autoStart = autoStart,
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
                    actualTemp = actualTemp,
                    startListeningEvents = sensorState::startListening,
                    stopListeningEvents = sensorState::stopListening
                )
            }
        }
    )

    return relativeHumiditySensorState.value
}
